package com.shop.utils.layers;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface Service<T> {
    
    List<T> getAll();

    T get(Long id);

    void add(T entityDTO);

    void delete(Long id);

    void update(T entityDTO);
}