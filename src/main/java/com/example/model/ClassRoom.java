package com.example.model;

/**
 * Сущность учебный класс, в которых учатся ученики.
 * Связи:
 * One To Many: ClassRoom -> Learners
 */
public class ClassRoom {
    private Long id;
    private String classCode;

    public ClassRoom() {
    }

    public ClassRoom(Long id, String classCode) {
        this.id = id;
        this.classCode = classCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
