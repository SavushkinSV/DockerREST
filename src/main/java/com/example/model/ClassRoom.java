package com.example.model;

/**
 * Сущность учебный класс, в которых учатся ученики.
 * Связи:
 * OneToMany: ClassRoom -> Learner
 */
public class ClassRoom {
    private Long id;
    private String code;

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

}
