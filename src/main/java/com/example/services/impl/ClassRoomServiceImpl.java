package com.example.services.impl;

import com.example.model.ClassRoom;
import com.example.repository.ClassRoomRepository;
import com.example.repository.impl.ClassRoomRepositoryImpl;
import com.example.services.ClassRoomService;
import jakarta.ejb.ObjectNotFoundException;

import java.util.List;

public class ClassRoomServiceImpl implements ClassRoomService {
    private static final ClassRoomRepository repository = ClassRoomRepositoryImpl.getInstance();
    private static ClassRoomService instance;

    private ClassRoomServiceImpl() {
    }

    public static ClassRoomService getInstance() {
        if (instance == null) {
            instance = new ClassRoomServiceImpl();
        }

        return instance;
    }

    @Override
    public ClassRoom getById(Long id) throws ObjectNotFoundException {
        return repository.getById(id);
    }

    @Override
    public List<ClassRoom> getAll() {
        return repository.getAll();
    }

    @Override
    public ClassRoom add(ClassRoom classRoom) {
        return repository.add(classRoom);
    }

    @Override
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public void update(ClassRoom classRoom) {
        repository.update(classRoom);
    }
}
