package com.shop.dao;

import com.shop.entity.Category;
import com.shop.utils.layers.DAO;

import java.util.List;


public interface CategoryDAO extends DAO<Category> {

    List<Object[]> getAllCategoriesWithCountProducts();
    Category getCategoryByName(String name);
}