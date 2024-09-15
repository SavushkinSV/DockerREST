package com.example.servlet.mapper;

import com.example.model.Learner;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;

import java.util.List;

public interface LearnerDtoMapper {
    Learner map(LearnerRequestDto requestDto);

    LearnerResponseDto map(Learner learner);

    List<LearnerResponseDto> map(List<Learner> learnerList);
}
