package com.example.models;

import java.util.List;

public class Learner {
    private Long id;
    private String firstName;
    private String lastName;
    private String classCode;
    private List<Subject> subjectList;

    public Learner() {
    }

    public Learner(Long id, String firstName, String lastName, String classCode, List<Subject> subjectList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classCode = classCode;
        this.subjectList = subjectList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
