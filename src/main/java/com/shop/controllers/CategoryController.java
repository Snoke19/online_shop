package com.shop.controllers;

import com.google.gson.Gson;
import com.shop.service.CategoryService;
import com.shop.dto.category.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDTO> allCategories(){
        return categoryService.getAll();
    }

    @PostMapping("/add/new/category")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO){
        System.out.println(categoryDTO);

        CategoryDTO categoryByName = categoryService.getCategoryByName(categoryDTO.getName());

        if (categoryByName != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Gson().toJson("This category exists already!"));
        }else {
            categoryService.add(categoryDTO);
            return ResponseEntity.ok().build();
        }
    }
}