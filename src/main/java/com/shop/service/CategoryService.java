package com.shop.service;

import com.shop.dto.category.CategoryDTO;
import com.shop.entity.Category;
import com.shop.utils.layers.Service;

import java.util.Map;


public interface CategoryService extends Service<CategoryDTO> {

    Map<String, Long> getAllCategoriesWithCountProducts();
    CategoryDTO getCategoryByName(String name);
}