package com.shop.utils.layers;

import java.util.List;

public interface Service<T> {
    
    List<T> getAll();
    T get(Long id);
    void add(T entityDTO);
    boolean delete(Long id);
    void update(T entityDTO);
}