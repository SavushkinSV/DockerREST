package com.example.repository;

import com.example.model.Rating;

import java.util.List;

public interface RatingRepository extends IRepository<Rating, Long>{
    List<Rating> getAllByLearnerId(Long learnerId);
}
