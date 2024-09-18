package com.example.servlet.dto;

public class ClassRoomDto {

    private Long id;
    private String code;

    public ClassRoomDto() {
    }

    public ClassRoomDto(Long id, String code) {
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
