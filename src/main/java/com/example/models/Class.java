package com.example.models;

public class Class {
    private Long id;
    private String classCode;

    public Class() {
    }

    public Class(Long id, String classCode) {
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
