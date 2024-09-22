package com.example.servlet.dto;

import com.example.model.ClassRoom;
import com.example.model.Rating;

import java.util.List;

/**
 * Ответ.
 */
public class LearnerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private ClassRoomResponseDto classRoom;
    private List<Rating> ratingList;

    public LearnerResponseDto(Long id, String firstName, String lastName, ClassRoomResponseDto classRoom, List<Rating> ratingList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classRoom = classRoom;
        this.ratingList = ratingList;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ClassRoomResponseDto getClassRoom() {
        return classRoom;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }
}
