package com.example.repository;

import java.util.List;

public interface IRepository<T, K> {
    T add(T entity);
    void update(T entity);
    boolean deleteById(K id);
    T getById(K id);
    List<T> getAll();
}
