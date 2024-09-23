package com.example.services;

import jakarta.ejb.ObjectNotFoundException;

import java.util.List;

public interface IService<T> {
    T getById(Long id) throws ObjectNotFoundException;

    List<T> getAll();

    T add(T entity);

    boolean delete(Long id);

    void update(T entity);
}
