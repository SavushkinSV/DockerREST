package repository;

import com.example.model.Learner;
import com.example.repository.LearnerRepository;
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

import java.util.Optional;


public class LearnerRepositoryImplTest {
    private static final String INIT_SQL = "sql/schema.sql";

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
            .withInitScript(INIT_SQL);

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
        databaseDelegate = new JdbcDatabaseDelegate(postgres, "");
    }

    @BeforeEach
    public void beforeEach() {
        ScriptUtils.runInitScript(databaseDelegate, "sql/schema.sql");
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1; true",
            "4; true",
            "10; false"
    }, delimiter = ';')
    public void getByIdTest(Long expectedId, Boolean expectedValue) {
        Optional<Learner> learner = learnerRepository.getById(expectedId);
        Assertions.assertEquals(expectedValue, learner.isPresent());
        learner.ifPresent(value -> Assertions.assertEquals(expectedId, value.getId()));
    }

    @Test
    public void getByIdNegativeTest() {
        Optional<Learner> learner = learnerRepository.getById(15L);

        Assertions.assertFalse(learner.isPresent());
    }

    @Test
    public void getAllTest(){
        int expectedSize = 5;
        int resultSize = learnerRepository.getAll().size();

        Assertions.assertEquals(expectedSize, resultSize);
    }
}
