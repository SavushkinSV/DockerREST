package com.example.servlet.mapper.impl;

import com.example.model.Learner;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;
import com.example.servlet.mapper.ClassRoomDtoMapper;
import com.example.servlet.mapper.LearnerDtoMapper;

import java.util.List;

public class LearnerDtoMapperImpl implements LearnerDtoMapper {
    private static LearnerDtoMapper instance;
    private static final ClassRoomDtoMapper classRoomDtoMapper = ClassRoomDtoMapperImpl.getInstance();

    private LearnerDtoMapperImpl() {
    }

    public static LearnerDtoMapper getInstance() {
        if (instance == null) {
            instance = new LearnerDtoMapperImpl();
        }

        return instance;
    }

    /**
     * Преобразует DTO в сущность.
     *
     * @param dto объект передачи данных
     * @return сущность
     */
    @Override
    public Learner map(LearnerRequestDto dto) {
        return new Learner(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getClassRoom(),
                null
        );
    }

    /**
     * Преобразует сущность в DTO.
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
                classRoomDtoMapper.map(learner.getClassRoom()),
                null
        );
    }

    /**
     * Преобразует коллекцию сущностей в DTO.
     *
     * @param learnerList коллекция сущностей
     * @return коллекция DTO
     */
    @Override
    public List<LearnerResponseDto> map(List<Learner> learnerList) {
        List<LearnerResponseDto> responseDtos;
        responseDtos = learnerList.stream().map(this::map).toList();

        return responseDtos;
    }
}
