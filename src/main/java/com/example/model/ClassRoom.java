package com.example.model;

import com.example.repository.LearnerRepository;
import com.example.repository.impl.LearnerRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность учебный класс, в которых учатся ученики.
 * Связи:
 * OneToMany: ClassRoom -> Learner
 */
public class ClassRoom {
    private Long id;
    private String code;
    private List<Learner> learnerList = new ArrayList<>();
    private static final LearnerRepository learnerRepository = LearnerRepositoryImpl.getInstance();

    public ClassRoom() {
    }

    public ClassRoom(Long id, String code, List<Learner> learnerList) {
        this.id = id;
        this.code = code;
        if (learnerList != null) {
            this.learnerList = learnerList;
        }
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
        if (this.learnerList.isEmpty()) {
            this.learnerList = learnerRepository.getAllByClassRoomId(this.id);
        }
        return learnerList;
    }

    public void setLearnerList(List<Learner> learnerList) {
        if (learnerList != null) {
            this.learnerList = learnerList;
        }
    }
}
