package com.example.model;

import java.util.List;

/**
 * Сущность учебный класс, в которых учатся ученики.
 * Связи:
 * OneToMany: ClassRoom -> Learner
 */
public class ClassRoom {
    private Long id;
    private String classCode;
    private List<Learner> learnerList;

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
