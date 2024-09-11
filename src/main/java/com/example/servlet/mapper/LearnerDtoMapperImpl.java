package com.example.servlet.mapper;

import com.example.model.Learner;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;

public class LearnerDtoMapperImpl implements LearnerDtoMapper{
    private static LearnerDtoMapper instance;

    private LearnerDtoMapperImpl() {
    }

    public static LearnerDtoMapper getInstance() {
        if (instance == null) {
            instance = new LearnerDtoMapperImpl();
        }

        return instance;
    }

    @Override
    public Learner map(LearnerRequestDto requestDto) {
        return null;
    }

    @Override
    public LearnerResponseDto map(Learner entity) {
        return null;
    }
}
