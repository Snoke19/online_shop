package com.shop.service.impl;

import com.shop.dao.CategoryDAO;
import com.shop.dto.category.CategoryDTO;
import com.shop.dto.category.CategoryMapper;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    @Autowired
    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    @Transactional
    public List<CategoryDTO> getAll() {
        return CategoryMapper.mapper.categoriesToCategoriesDTO(categoryDAO.getAll());
    }

    @Override
    @Transactional
    public CategoryDTO get(Long id) {
        return CategoryMapper.mapper.categoryToCategoryDTO(categoryDAO.get(id));
    }

    @Override
    @Transactional
    public void add(CategoryDTO entityDTO) {
        categoryDAO.add(CategoryMapper.mapper.categoryDTOToCategory(entityDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryDAO.delete(id);
    }

    @Override
    @Transactional
    public void update(CategoryDTO entityDTO) {
        categoryDAO.update(CategoryMapper.mapper.categoryDTOToCategory(entityDTO));
    }

    @Override
    @Transactional
    public Map<String, Long> getAllCategoriesWithCountProducts() {
        List<Object[]> list = categoryDAO.getAllCategoriesWithCountProducts();
        return list.stream().collect(Collectors.toMap(object -> (String)object[0], object -> (Long)object[1]));
    }

    @Override
    @Transactional
    public CategoryDTO getCategoryByName(String name) {
        return CategoryMapper.mapper.categoryToCategoryDTO(categoryDAO.getCategoryByName(name));
    }
}