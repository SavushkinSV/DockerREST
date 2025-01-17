package com.example.servlet.dto;

import com.example.model.ClassRoom;

/**
 * Запрос от клиента.
 */
public class LearnerRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private ClassRoom classRoom;

    public LearnerRequestDto() {
    }

    public LearnerRequestDto(Long id, String firstName, String lastName, ClassRoom classRoom) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classRoom = classRoom;
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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

}
