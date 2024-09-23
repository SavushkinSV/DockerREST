package com.example.service.impl;

import com.example.exception.ObjectNotFoundException;
import com.example.model.ClassRoom;
import com.example.repository.ClassRoomRepository;
import com.example.repository.impl.ClassRoomRepositoryImpl;
import com.example.services.ClassRoomService;
import com.example.services.impl.ClassRoomServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.lang.reflect.Field;

 class ClassRoomServiceImplTest {
    private static ClassRoomService service;
    private static ClassRoomRepository repository;
    private static final ClassRoom mockClassRoom = new ClassRoom(
            1L,
            "1а",
            null
    );

    @BeforeAll
    static void beforeAll() {
        repository = Mockito.mock(ClassRoomRepositoryImpl.class);
        try {
            Field instance = ClassRoomRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        service = ClassRoomServiceImpl.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        Mockito.reset(repository);
    }

    @AfterAll
    static void afterAllTest() {
        try {
            Field instance = ClassRoomRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getByIdTest() throws ObjectNotFoundException {
        Long expectedId = mockClassRoom.getId();
        Mockito.doReturn(mockClassRoom).when(repository).getById(Mockito.anyLong());

        ClassRoom result = service.getById(expectedId);

        Assertions.assertEquals(expectedId, result.getId());
    }

     @Test
     void getAllTest() {
         service.getAll();

         Mockito.verify(repository).getAll();
     }

     @Test
     void saveTest() {
         Long expectedId = mockClassRoom.getId();
         Mockito.doReturn(mockClassRoom).when(repository).add(Mockito.any(ClassRoom.class));

         ClassRoom result = service.add(mockClassRoom);

         Assertions.assertEquals(expectedId, result.getId());
     }

     @Test
     void deleteByIdTest() {
         Mockito.doReturn(true).when(repository).deleteById(Mockito.any(Long.class));

         boolean result = service.delete(5L);

         Assertions.assertTrue(result);
     }

     @Test
     void updateTest() {
         service.update(mockClassRoom);

         Mockito.verify(repository).update(mockClassRoom);
     }
}
