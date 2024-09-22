package com.example.service.impl;

import com.example.model.Rating;
import com.example.repository.RatingRepository;
import com.example.repository.impl.RatingRepositoryImpl;
import com.example.services.RatingService;
import com.example.services.impl.RatingServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;

class RatingServiceImplTest {
    private static RatingService service;
    private static RatingRepository repository;
    private static RatingRepositoryImpl oldInstance;
    private static final Rating mockRating = new Rating(
            1L,
            "2024-01-01",
            1,
            "Test"
    );

    @BeforeAll
    static void beforeAll() {
        repository = Mockito.mock(RatingRepositoryImpl.class);
        try {
            Field instance = RatingRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (RatingRepositoryImpl) instance.get(instance);
            instance.set(instance, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        service = RatingServiceImpl.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        Mockito.reset(repository);
    }

    @AfterAll
    static void afterAllTest() {
        try {
            Field instance = RatingRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, oldInstance);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getByIdTest() {
        Long expectedId = mockRating.getId();
        Mockito.doReturn(mockRating).when(repository).getById(Mockito.anyLong());

        Rating rating = service.getById(expectedId);

        Assertions.assertEquals(expectedId, rating.getId());
    }

    @Test
    void getAllTest() {
        service.getAll();

        Mockito.verify(repository).getAll();
    }

    @Test
    void saveTest() {
        Long expectedId = mockRating.getId();
        Mockito.doReturn(mockRating).when(repository).add(Mockito.any(Rating.class));

        Rating rating = service.add(mockRating);

        Assertions.assertEquals(expectedId, rating.getId());
    }

    @Test
    void deleteByIdTest() {
        Mockito.doReturn(true).when(repository).deleteById(Mockito.any(Long.class));

        boolean result = service.delete(5L);

        Assertions.assertTrue(result);
    }

    @Test
    void updateTest() {
        service.update(mockRating);

        Mockito.verify(repository).update(mockRating);
    }
}
