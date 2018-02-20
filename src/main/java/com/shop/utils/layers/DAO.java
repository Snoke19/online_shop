package com.shop.utils.layers;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();
    T get(Long id);
    void add(T entity);
    void delete(Long id);
    void update(T entity);
}