package com.example.servlet.dto;

/**
 * Запрос от клиента.
 */
public class RatingRequestDto {
    private Long id;
    private String date;
    private Integer value;
    private String subjectName;

    public RatingRequestDto() {
    }

    public RatingRequestDto(Long id, String date, Integer value, String subjectName) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Integer getValue() {
        return value;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
