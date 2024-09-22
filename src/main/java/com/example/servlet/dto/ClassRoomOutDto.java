package com.example.servlet.dto;

public class ClassRoomOutDto {
    Long id;
    String code;

    public ClassRoomOutDto() {
    }

    public ClassRoomOutDto(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
