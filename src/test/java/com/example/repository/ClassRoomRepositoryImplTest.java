package com.example.repository;

import com.example.exception.ObjectNotFoundException;
import com.example.model.ClassRoom;
import com.example.repository.impl.ClassRoomRepositoryImpl;
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

public class ClassRoomRepositoryImplTest {
    private static final String INIT_PATH = "db-migration.sql";
    private static final String DB_NAME = PropertiesUtil.getProperty("db.name");
    private static final String DB_USER = PropertiesUtil.getProperty("db.user");
    private static final String DB_PASSWORD = PropertiesUtil.getProperty("db.password");
    private static final int DB_CONTAINER_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.container_port"));
    private static final int DB_LOCAL_PORT = Integer.parseInt(PropertiesUtil.getProperty("db.local_port"));

    private static final ClassRoomRepository repository = ClassRoomRepositoryImpl.getInstance();
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
        ClassRoom classRoom = repository.getById(expectedId);

        Assertions.assertNotNull(classRoom);
        Assertions.assertEquals(expectedId, classRoom.getId());
    }

    @Test
    void getByIdNegativeTest() {
        Assertions.assertThrows(ObjectNotFoundException.class, () -> repository.getById(15L));
    }

    @Test
    void deleteByIdTest() {
        boolean expected = true;
        int expectedSize = repository.getAll().size() - 1;

        boolean result = repository.deleteById(5L);
        int resultSize = repository.getAll().size();

        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void updateTest() throws ObjectNotFoundException{
        String expectedCode = "10б";
        Long expectedId = 5L;

        ClassRoom classRoomUpdate = repository.getById(expectedId);
        classRoomUpdate.setCode(expectedCode);
        repository.update(classRoomUpdate);

        ClassRoom classRoomAfterUpdate = repository.getById(expectedId);

        Assertions.assertEquals(expectedCode, classRoomAfterUpdate.getCode());
    }

    @Test
    void addTest() throws ObjectNotFoundException{
        String expectedCode = "10б";
        ClassRoom classRoom = new ClassRoom(null, expectedCode, null);

        classRoom = repository.add(classRoom);
        ClassRoom addClassRoom = repository.getById(classRoom.getId());

        Assertions.assertEquals(expectedCode, addClassRoom.getCode());
    }

}
