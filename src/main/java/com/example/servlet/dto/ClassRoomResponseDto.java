package com.example.servlet.dto;

import com.example.model.Learner;

import java.util.List;

public class ClassRoomResponseDto {
    private Long id;
    private String code;

    public ClassRoomResponseDto() {
    }

    public ClassRoomResponseDto(Long id, String code) {
        this.id = id;
        this.code = code;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
