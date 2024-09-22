package com.example.servlet.dto;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomResponseDto {
    private Long id;
    private String code;
    private List<LearnerResponseDto> learnerList = new ArrayList<>();

    public ClassRoomResponseDto() {
    }

    public ClassRoomResponseDto(Long id, String code, List<LearnerResponseDto> learnerList) {
        this.id = id;
        this.code = code;
        if (learnerList != null)
            this.learnerList = learnerList;
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

    public List<LearnerResponseDto> getLearnerList() {
        return learnerList;
    }

    public void setLearnerList(List<LearnerResponseDto> learnerList) {
        this.learnerList = learnerList;
    }
}
