package com.example.repositoty.impl;

import com.example.model.ClassRoom;
import com.example.model.Learner;
import com.example.repository.LearnerRepository;
import com.example.repository.impl.LearnerRepositoryImpl;
import com.example.services.LearnerService;
import com.example.services.impl.LearnerServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class LearnerServiceImplTest {

    private static LearnerService service;
    private static LearnerRepository repository;
    private static ClassRoom classRoom;
    private static LearnerRepositoryImpl oldInstance;

    @BeforeAll
    static void beforeAll() {
        classRoom = new ClassRoom(1L, "1а");
//        Создаем mock класса LearnerRepository
        repository = Mockito.mock(LearnerRepositoryImpl.class);
        try {
            Field instance = LearnerRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (LearnerRepositoryImpl) instance.get(instance);
            instance.set(instance, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        service = LearnerServiceImpl.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        Mockito.reset(repository);
    }

    @AfterAll
    static void afterAllTest() {
        try {
            Field instance = LearnerRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, oldInstance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getByIdTest() {
        Long expectedId = 1L;
        Learner mockLearner = new Learner(
                expectedId,
                "Test",
                "Test",
                classRoom,
                null
        );
        Mockito.doReturn(mockLearner).when(repository).getById(Mockito.anyLong());

        Learner learner = service.getById(expectedId);

        Assertions.assertEquals(expectedId, learner.getId());
    }

    @Test
    public void getAllTest() {
        service.getAll();

        Mockito.verify(repository).getAll();
    }

    @Test
    void saveTest() {
        Long expectedId = 1L;
        Learner mockLearner = new Learner(
                expectedId,
                "Test",
                "Test",
                classRoom,
                null
        );
        Mockito.doReturn(mockLearner).when(repository).add(Mockito.any(Learner.class));

        Learner learner = service.add(mockLearner);

        Assertions.assertEquals(expectedId, learner.getId());
    }

    @Test
    public void deleteByIdTest() {
        Mockito.doReturn(true).when(repository).deleteById(Mockito.any(Long.class));
        boolean result = service.delete(5L);

        Assertions.assertTrue(result);
    }

    @Test
    void updateTest() {
        Long expectedId = 1L;
        Learner mockLearner = new Learner(
                expectedId,
                "Test",
                "Test",
                classRoom,
                null
        );

        service.update(mockLearner);

        Mockito.verify(repository).update(mockLearner);
    }



}
