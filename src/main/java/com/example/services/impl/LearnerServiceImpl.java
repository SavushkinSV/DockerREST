package com.example.services.impl;

import com.example.model.Learner;
import com.example.repository.LearnerRepository;
import com.example.repository.impl.LearnerRepositoryImpl;
import com.example.services.LearnerService;

import java.util.List;

public class LearnerServiceImpl implements LearnerService {
    private static final LearnerRepository learnerRepository = LearnerRepositoryImpl.getInstance();

    private static LearnerService instance;

    private LearnerServiceImpl() {
    }

    public static LearnerService getInstance() {
        if (instance == null) {
            instance = new LearnerServiceImpl();
        }

        return instance;
    }

    /**
     * Возвращает сущность по идентификатору.
     *
     * @param id идентификатор
     * @return сущность
     */
    @Override
    public Learner getById(Long id) {

        return learnerRepository.getById(id);
    }

    @Override
    public List<Learner> getAll() {

        return learnerRepository.getAll();
    }

    @Override
    public Learner add(Learner learner) {

        return learnerRepository.add(learner);
    }

    @Override
    public void delete(Long id) {

        learnerRepository.deleteById(id);
    }
}
