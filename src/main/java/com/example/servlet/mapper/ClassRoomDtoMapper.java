package com.example.servlet.mapper;

import com.example.model.ClassRoom;
import com.example.servlet.dto.ClassRoomRequestDto;
import com.example.servlet.dto.ClassRoomResponseDto;

import java.util.List;

public interface ClassRoomDtoMapper {

    ClassRoom map(ClassRoomRequestDto dto);

    ClassRoomResponseDto map(ClassRoom classRoom);

    List<ClassRoomResponseDto> map(List<ClassRoom> classRoomList);


}
