package com.example.servlet.mapper;

import com.example.model.Learner;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Принимает запрос.
     *
     * @param requestDto
     * @return
     */
    @Override
    public Learner map(LearnerRequestDto requestDto) {
        return new Learner(
                null,
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getClassRoom(),
                null
        );
    }

    /**
     *  Направляет ответ.
     *
     * @param learner сущность
     * @return DTO
     */
    @Override
    public LearnerResponseDto map(Learner learner) {
        return new LearnerResponseDto(
                learner.getId(),
                learner.getFirstName(),
                learner.getLastName(),
                null,
                null
        );
    }

    @Override
    public List<LearnerResponseDto> map(List<Learner> learnerList) {
        List<LearnerResponseDto> learnerResponseDtoList = new ArrayList<>();
        learnerResponseDtoList = learnerList.stream().map(this::map).toList();

        return learnerResponseDtoList;
    }
}
