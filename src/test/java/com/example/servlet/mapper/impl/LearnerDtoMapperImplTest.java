package com.example.servlet.mapper.impl;

import com.example.model.ClassRoom;
import com.example.model.Learner;
import com.example.servlet.dto.LearnerRequestDto;
import com.example.servlet.dto.LearnerResponseDto;
import com.example.servlet.mapper.LearnerDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LearnerDtoMapperImplTest {
    private static final LearnerDtoMapper mapper = LearnerDtoMapperImpl.getInstance();

    @Test
    public void mapDtoToLearnerTest() {
        LearnerRequestDto dto = new LearnerRequestDto(
                1L,
                "firstName",
                "lastName",
                new ClassRoom(1L, "1а")
        );

        Learner result = mapper.map(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getFirstName(), result.getFirstName());
        Assertions.assertEquals(dto.getLastName(), result.getLastName());
        Assertions.assertNotNull(result.getClassRoom());
        Assertions.assertEquals(dto.getClassRoom().getId(), result.getClassRoom().getId());
        Assertions.assertEquals(dto.getClassRoom().getCode(), result.getClassRoom().getCode());
    }

    @Test
    public void mapLearnerToDtoTest() {
        Learner learner = new Learner(
                1L,
                "firstName",
                "lastName",
                new ClassRoom(1L, "1а"),
                null
        );

        LearnerResponseDto result = mapper.map(learner);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(learner.getId(), result.getId());
        Assertions.assertEquals(learner.getFirstName(), result.getFirstName());
        Assertions.assertEquals(learner.getLastName(), result.getLastName());
        Assertions.assertNotNull(result.getClassRoom());
        Assertions.assertEquals(learner.getClassRoom().getId(), result.getClassRoom().getId());
        Assertions.assertEquals(learner.getClassRoom().getCode(), result.getClassRoom().getCode());
    }

    @Test
    public void mapListLearnerToDtoTest() {
        List<Learner> learnerList = new ArrayList<>();
        learnerList.add(new Learner(1L, "firstName0", "lastName0", null, null));
        learnerList.add(new Learner(2L, "firstName1", "lastName1", null, null));
        learnerList.add(new Learner(3L, "firstName3", "lastName3", null, null));
        learnerList.add(new Learner(4L, "firstName4", "lastName4", null, null));

        List<LearnerResponseDto> resultList = mapper.map(learnerList);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(learnerList.size(), resultList.size());
        Assertions.assertEquals(learnerList.get(0).getId(), resultList.get(0).getId());
        Assertions.assertEquals(learnerList.get(1).getFirstName(), resultList.get(1).getFirstName());
        Assertions.assertEquals(learnerList.get(2).getLastName(), resultList.get(2).getLastName());
        Assertions.assertNull(resultList.get(3).getClassRoom());
        Assertions.assertNull(resultList.get(3).getRatingList());
    }
}
