package com.example.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, K> {
    T add(T entity);
    void update(T entity);
    void delete(K id);
    Optional<T> getById(K id);
    List<T> getAll();
}
