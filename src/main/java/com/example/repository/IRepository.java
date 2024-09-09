package com.example.repository;

import java.util.List;

public interface IRepository<T, K> {
    void add(T entity);
    void update(T entity);
    void delete(K id);
    T getById(K id);
    List<T> getAll();
}
