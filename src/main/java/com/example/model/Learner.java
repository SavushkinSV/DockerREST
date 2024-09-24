package com.example.model;

import com.example.repository.RatingRepository;
import com.example.repository.impl.RatingRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность ученик.
 * Связи:
 * ManyToOne: Learner -> ClassRoom
 * ManyToMany Learner <-> Rating
 */
public class Learner {
    private Long id;
    private String firstName;
    private String lastName;
    private ClassRoom classRoom;
    private List<Rating> ratingList = new ArrayList<>();

    private static final RatingRepository ratingRepository = RatingRepositoryImpl.getInstance();

    public Learner() {
    }

    public Learner(Long id, String firstName, String lastName, ClassRoom classRoom, List<Rating> ratingList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classRoom = classRoom;
        this.ratingList = ratingList;
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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public List<Rating> getRatingList() {
        if (this.ratingList.isEmpty()) {
            this.ratingList = ratingRepository.getAllByLearnerId(this.id);
        }
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingtList) {
        if (ratingtList != null) {
            this.ratingList = ratingtList;
        }
    }

}
