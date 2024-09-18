package com.example.model;

import java.util.List;

/**
 * Сущность оценки.
 * Связи:
 * ManeToMany <-> Learner
 */
public class Rating {
    private Long id;
    private String date;
    private Integer value;
    private String subjectName;
    private List<Learner> learnerList;

    public Rating() {
    }

    public Rating(Long id, String date, Integer value, String subjectName) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Learner> getLearnerList() {
        return learnerList;
    }

    public void setLearnerList(List<Learner> learnerList) {
        this.learnerList = learnerList;
    }
}
