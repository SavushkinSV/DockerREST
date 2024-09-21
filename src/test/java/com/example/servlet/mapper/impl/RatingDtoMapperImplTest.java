package com.example.servlet.mapper.impl;

import com.example.model.Rating;
import com.example.servlet.dto.RatingRequestDto;
import com.example.servlet.dto.RatingResponseDto;
import com.example.servlet.mapper.RatingDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RatingDtoMapperImplTest {
    private static final RatingDtoMapper mapper = RatingDtoMapperImpl.getInstance();

    @Test
    void mapDtoToRatingTest() {
        RatingRequestDto dto = new RatingRequestDto(
                1L,
                "2021-01-01",
                5,
                "Test"
        );

        Rating result = mapper.map(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getDate(), result.getDate());
        Assertions.assertEquals(dto.getValue(), result.getValue());
        Assertions.assertEquals(dto.getSubjectName(), result.getSubjectName());
    }

    @Test
    void mapRatingToDtoTest() {
        Rating rating = new Rating(
                1L,
                "2021-01-01",
                5,
                "Test"
        );

        RatingResponseDto result = mapper.map(rating);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(rating.getId(), result.getId());
        Assertions.assertEquals(rating.getDate(), result.getDate());
        Assertions.assertEquals(rating.getValue(), result.getValue());
        Assertions.assertEquals(rating.getSubjectName(), result.getSubjectName());
    }

    @Test
    void mapListRatingToDtoTest() {
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(new Rating(1L, "2021-01-01", 0, "Test0"));
        ratingList.add(new Rating(2L, "2021-01-01", 1, "Test1"));
        ratingList.add(new Rating(3L, "2021-01-01", 2, "Test2"));
        ratingList.add(new Rating(4L, "2021-01-01", 3, "Test3"));

        List<RatingResponseDto> resultList = mapper.map(ratingList);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(ratingList.size(), resultList.size());
        Assertions.assertEquals(ratingList.get(0).getId(), resultList.get(0).getId());
        Assertions.assertEquals(ratingList.get(1).getDate(), resultList.get(1).getDate());
        Assertions.assertEquals(ratingList.get(2).getValue(), resultList.get(2).getValue());
        Assertions.assertEquals(ratingList.get(3).getSubjectName(), resultList.get(3).getSubjectName());
    }


}
