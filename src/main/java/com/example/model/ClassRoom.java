package com.example.model;

import com.example.repository.LearnerRepository;
import com.example.repository.impl.LearnerRepositoryImpl;

import java.util.List;

/**
 * Сущность учебный класс, в которых учатся ученики.
 * Связи:
 * OneToMany: ClassRoom -> Learner
 */
public class ClassRoom {
    private Long id;
    private String code;
    private List<Learner> learnerList;
    private static final LearnerRepository learnerRepository = LearnerRepositoryImpl.getInstance();

    public ClassRoom() {
    }

    public ClassRoom(Long id, String code) {
        this.id = id;
        this.code = code;
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

    public List<Learner> getLearnerList() {
        if (this.learnerList == null) {
            this.learnerList = learnerRepository.getAllByClassRoomId(this.id);
        }
        return learnerList;
    }

    public void setLearnerList(List<Learner> learnerList) {
        this.learnerList = learnerList;
    }
}
