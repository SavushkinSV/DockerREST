package com.example.services.impl;

import com.example.exception.ObjectNotFoundException;
import com.example.model.Learner;
import com.example.repository.LearnerRepository;
import com.example.repository.impl.LearnerRepositoryImpl;
import com.example.services.LearnerService;

import java.util.List;

public class LearnerServiceImpl implements LearnerService {
    private static LearnerService instance;
    private static final LearnerRepository learnerRepository = LearnerRepositoryImpl.getInstance();

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
    public Learner getById(Long id) throws ObjectNotFoundException {
        return learnerRepository.getById(id);
    }

    /**
     * Возвращает все сущности.
     *
     * @return коллекция сущностей
     */
    @Override
    public List<Learner> getAll() {
        return learnerRepository.getAll();
    }

    /**
     * Добавляет сущность.
     *
     * @param learner сущность
     * @return сущность
     */
    @Override
    public Learner add(Learner learner) {
        return learnerRepository.add(learner);
    }

    /**
     * Добавляет сущность по идентификатору.
     *
     * @param id идентификатор
     * @return {@code true} если сущность удалена
     */
    @Override
    public boolean delete(Long id) {
        return learnerRepository.deleteById(id);
    }

    /**
     * Обновляет данные сущности.
     *
     * @param learner сущность
     */
    @Override
    public void update(Learner learner) {
        learnerRepository.update(learner);
    }
}
