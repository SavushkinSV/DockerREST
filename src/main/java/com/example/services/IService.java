package com.example.services;

import com.example.exception.ObjectNotFoundException;

import java.util.List;

public interface IService<T> {
    T getById(Long id) throws ObjectNotFoundException;

    List<T> getAll();

    T add(T entity);

    boolean delete(Long id);

    void update(T entity);
}
