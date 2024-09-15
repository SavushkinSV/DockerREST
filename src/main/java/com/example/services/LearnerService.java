package com.example.services;

import com.example.model.Learner;

import java.util.List;

public interface LearnerService {
    Learner getById(Long id);

    List<Learner> getAll();

}
