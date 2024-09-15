package com.example.repository;

import com.example.model.Learner;
import com.example.repository.impl.LearnerRepositoryImpl;
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

public class LearnerRepositoryImplTest {
    private static final String INIT_PATH = "db-migration.sql";
    private static final String DB_NAME = PropertiesUtil.getProperty("db.name");
    private static final String DB_USER = PropertiesUtil.getProperty("db.user");
    private static final String DB_PASSWORD = PropertiesUtil.getProperty("db.password");
    private static final int DB_CONTAINER_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.container_port"));
    private static final int DB_LOCAL_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.local_port"));

    private static final LearnerRepository learnerRepository = LearnerRepositoryImpl.getInstance();
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
    public void getByIdTest(Long expectedId) {
        Learner learner = learnerRepository.getById(expectedId);

        Assertions.assertNotNull(learner);
        Assertions.assertEquals(expectedId, learner.getId());
    }

    @Test
    public void getByIdNegativeTest() {
        Learner learner = learnerRepository.getById(15L);

        Assertions.assertNull(learner);
    }

    @Test
    public void getAllTest() {
        int expectedSize = 6;
        int resultSize = learnerRepository.getAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    public void deleteByIdTest() {
        boolean expected = true;
        int expectedSize = learnerRepository.getAll().size() - 1;

        boolean result = learnerRepository.deleteById(6L);
        int resultSize = learnerRepository.getAll().size();

        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    public void updateTest() {
        String expectedFirstName = "UpdateFirstName";
        String expectedLastName = "UpdateLastName";
        String expectedClassName = "2а";
        Long expectedId = 6L;

        Learner learnerUpdate = learnerRepository.getById(expectedId);
        learnerUpdate.setFirstName(expectedFirstName);
        learnerUpdate.setLastName(expectedLastName);
        learnerRepository.update(learnerUpdate);

        Learner learnerAfterUpdate = learnerRepository.getById(expectedId);

        Assertions.assertEquals(expectedFirstName, learnerAfterUpdate.getFirstName());
        Assertions.assertEquals(expectedLastName, learnerAfterUpdate.getLastName());
    }
}
