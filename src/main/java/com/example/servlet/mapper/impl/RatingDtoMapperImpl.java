package com.example.servlet.mapper.impl;

import com.example.model.Rating;
import com.example.servlet.dto.RatingRequestDto;
import com.example.servlet.dto.RatingResponseDto;
import com.example.servlet.mapper.RatingDtoMapper;

import java.util.List;

public class RatingDtoMapperImpl implements RatingDtoMapper {
    private static RatingDtoMapper instance;

    private RatingDtoMapperImpl() {
    }

    public static RatingDtoMapper getInstance() {
        if (instance == null) {
            instance = new RatingDtoMapperImpl();
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
    public Rating map(RatingRequestDto dto) {
        return new Rating(
                null,
                dto.getDate(),
                dto.getValue(),
                dto.getSubjectName()
        );
    }

    /**
     * Преобразует сущность в DTO.
     *
     * @param rating сущность
     * @return объект передачи данных
     */
    @Override
    public RatingResponseDto map(Rating rating) {
        return new RatingResponseDto(
                rating.getId(),
                rating.getDate(),
                rating.getValue(),
                rating.getSubjectName()
        );
    }

    /**
     * Преобразует коллекцию сущностей в DTO.
     *
     * @param ratingList коллекция сущностей
     * @return коллекция DTO
     */
    @Override
    public List<RatingResponseDto> map(List<Rating> ratingList) {
        List<RatingResponseDto> responseDtos;
        responseDtos = ratingList.stream().map(this::map).toList();

        return responseDtos;
    }
}
