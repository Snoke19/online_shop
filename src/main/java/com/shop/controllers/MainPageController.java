package com.shop.controllers;

import com.shop.service.CategoryService;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PutMapping("/sidebar/products/{category}")
    public ResponseEntity<Map<String, Collection<Map<String, Integer>>>> getSideBarProducts(
            @PathVariable("category") String category, @RequestBody Map<String, Object> filter){

        List<String> filterList = (List<String>) filter.get("allProducers");

        if (filterList == null){
            filterList = new ArrayList<>();
            return ResponseEntity.ok(productsService.getSideBarFilterProducts(category, filterList).asMap());
        }

        return ResponseEntity.ok(productsService.getSideBarFilterProducts(category, filterList).asMap());
    }
}