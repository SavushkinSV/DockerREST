package com.example.repository;

import com.example.exception.ObjectNotFoundException;
import com.example.model.Rating;
import com.example.repository.impl.RatingRepositoryImpl;
import com.example.util.PropertiesUtil;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;

public class RatingRepositoryImplTest {
    private static final String INIT_PATH = "db-migration.sql";
    private static final String DB_NAME = PropertiesUtil.getProperty("db.name");
    private static final String DB_USER = PropertiesUtil.getProperty("db.user");
    private static final String DB_PASSWORD = PropertiesUtil.getProperty("db.password");
    private static final int DB_CONTAINER_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.container_port"));
    private static final int DB_LOCAL_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.local_port"));

    private static final RatingRepository ratingRepository = RatingRepositoryImpl.getInstance();
    public static JdbcDatabaseDelegate databaseDelegate;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4")
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USER)
            .withPassword(DB_PASSWORD)
            .withExposedPorts(DB_CONTAINER_PORT)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(DB_LOCAL_PORT), new ExposedPort(DB_CONTAINER_PORT)))
            ))
            .withInitScript(INIT_PATH);

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
        databaseDelegate = new JdbcDatabaseDelegate(postgres, "");
    }

    @BeforeEach
    public void beforeEach() {
        ScriptUtils.runInitScript(databaseDelegate, INIT_PATH);
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "3", "5"})
     void getByIdTest(Long expectedId) throws ObjectNotFoundException {
        Rating rating = ratingRepository.getById(expectedId);

        Assertions.assertNotNull(rating);
        Assertions.assertEquals(expectedId, rating.getId());
    }

    @Test
     void getByIdNegativeTest(){
        Assertions.assertThrows(ObjectNotFoundException.class, () -> ratingRepository.getById(15L));
    }

    @Test
     void getAllTest() {
        int expectedSize = 9;
        int resultSize = ratingRepository.getAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
     void deleteByIdTest() {
        boolean expected = true;
        int expectedSize = ratingRepository.getAll().size() - 1;

        boolean result = ratingRepository.deleteById(9L);
        int resultSize = ratingRepository.getAll().size();

        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    public void deleteByIdNegativeTest() {
        boolean expected = false;
        int expectedSize = ratingRepository.getAll().size();

        boolean result = ratingRepository.deleteById(15L);
        int resultSize = ratingRepository.getAll().size();

        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    public void addTest() throws ObjectNotFoundException{
        String expectedDate = "2024-12-31";
        Integer expectedValue = 0;
        String expectedSubjectName = "Test";
        Rating rating = new Rating(
                null,
                expectedDate,
                expectedValue,
                expectedSubjectName
        );

        rating = ratingRepository.add(rating);
        Rating addRating = ratingRepository.getById(rating.getId());

        Assertions.assertEquals(expectedDate, addRating.getDate());
        Assertions.assertEquals(expectedValue, addRating.getValue());
        Assertions.assertEquals(expectedSubjectName, addRating.getSubjectName());
    }

    @Test
    public void updateTest() throws ObjectNotFoundException{
        String expectedDate = "2001-12-01";
        Integer expectedValue = 1;
        String expectedSubjectName = "Test";
        Long expectedId = 2L;

        Rating ratingUpdate = ratingRepository.getById(expectedId);
        ratingUpdate.setDate(expectedDate);
        ratingUpdate.setValue(expectedValue);
        ratingUpdate.setSubjectName(expectedSubjectName);
        ratingRepository.update(ratingUpdate);

        Rating ratingAfterUpdate = ratingRepository.getById(expectedId);

        Assertions.assertEquals(expectedDate, ratingAfterUpdate.getDate());
        Assertions.assertEquals(expectedValue, ratingAfterUpdate.getValue());
        Assertions.assertEquals(expectedSubjectName, ratingAfterUpdate.getSubjectName());
    }


}
