package com.example.repository;

import com.example.model.Learner;

import java.util.List;

public interface LearnerRepository extends IRepository<Learner,Long> {
    List<Learner> getAllByClassRoomId(Long classRoomId);
}
