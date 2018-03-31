package com.shop.controllers;

import com.google.common.collect.Multimap;
import com.shop.service.CategoryService;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
public class MainPageController {

    private CategoryService categoryService;
    private ProductsService productsService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping("/count/all/products")
    public int countAllProducts(){
        return productsService.getAll().size();
    }


    @GetMapping("/categories/count/product")
    public Map<String, Long> getAllCategoriesWithCountProducts(){
        return categoryService.getAllCategoriesWithCountProducts();
    }

    @GetMapping("/sidebar/products/{category}")
    public ResponseEntity<Map<String, Collection<Map<String, Integer>>>> getSideBarProducts(@PathVariable("category") String category){
        System.out.println(productsService.getSideBarFilterProducts(category));
        return ResponseEntity.ok(productsService.getSideBarFilterProducts(category).asMap());
    }
}