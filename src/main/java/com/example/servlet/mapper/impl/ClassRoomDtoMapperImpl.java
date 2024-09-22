package com.example.servlet.mapper.impl;

import com.example.model.ClassRoom;
import com.example.servlet.dto.ClassRoomRequestDto;
import com.example.servlet.dto.ClassRoomResponseDto;
import com.example.servlet.mapper.ClassRoomDtoMapper;

import java.util.List;

public class ClassRoomDtoMapperImpl implements ClassRoomDtoMapper {
    private static ClassRoomDtoMapperImpl instance;

    private ClassRoomDtoMapperImpl() {
    }

    public static ClassRoomDtoMapperImpl getInstance() {
        if (instance == null) {
            instance = new ClassRoomDtoMapperImpl();
        }

        return instance;
    }

    @Override
    public ClassRoom map(ClassRoomRequestDto dto) {
        return new ClassRoom(
                dto.getId(),
                dto.getCode()
        );
    }

    @Override
    public ClassRoomResponseDto map(ClassRoom classRoom) {
        if (classRoom == null) {
            return null;
        } else {
            return new ClassRoomResponseDto(
                    classRoom.getId(),
                    classRoom.getCode()
            );
        }
    }

    @Override
    public List<ClassRoomResponseDto> map(List<ClassRoom> classRoomList) {
        List<ClassRoomResponseDto> responseDtos;
        responseDtos = classRoomList.stream().map(this::map).toList();

        return responseDtos;
    }
}
