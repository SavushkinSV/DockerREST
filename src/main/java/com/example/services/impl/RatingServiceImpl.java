package com.example.services.impl;

import com.example.model.Rating;
import com.example.repository.RatingRepository;
import com.example.repository.impl.RatingRepositoryImpl;
import com.example.services.RatingService;

import java.util.List;

public class RatingServiceImpl implements RatingService {
    private static RatingService instance;
    private static final RatingRepository ratingRepository = RatingRepositoryImpl.getInstance();

    private RatingServiceImpl() {
    }

    public static RatingService getInstance() {
        if (instance == null) {
            instance = new RatingServiceImpl();
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
    public Rating getById(Long id) {
        return ratingRepository.getById(id);
    }

    /**
     * Возвращает все сущности.
     *
     * @return коллекция сущностей
     */
    @Override
    public List<Rating> getAll() {
        return ratingRepository.getAll();
    }

    @Override
    public Rating add(Rating rating) {
        return ratingRepository.add(rating);
    }

    @Override
    public boolean delete(Long id) {
        return ratingRepository.deleteById(id);
    }
}
