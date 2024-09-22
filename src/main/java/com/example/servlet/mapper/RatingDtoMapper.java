package com.example.servlet.mapper;

import com.example.model.Rating;
import com.example.servlet.dto.RatingRequestDto;
import com.example.servlet.dto.RatingResponseDto;

import java.util.List;

public interface RatingDtoMapper {
    Rating map(RatingRequestDto dto);

    RatingResponseDto map(Rating rating);

    List<RatingResponseDto> map(List<Rating> ratingList);

}
