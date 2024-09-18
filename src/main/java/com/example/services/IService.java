package com.example.services;

import java.util.List;

public interface IService<T> {
    T getById(Long id);

    List<T> getAll();

    T add(T entity);

    boolean delete(Long id);
}
