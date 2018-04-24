package com.shop.controllers;

import com.shop.service.CategoryService;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<Map<String, Long>> getAllCategoriesWithCountProducts(){
        return ResponseEntity.ok(categoryService.getAllCategoriesWithCountProducts());
    }

    @PutMapping("/sidebar/products/{category}")
    public ResponseEntity<Map<String, Collection<Map<String, Integer>>>> getSideBarProducts(
            @PathVariable("category") String category, @RequestBody Map<String, Object> filter){

        List<String> filterList = new ArrayList<>();

        if(filter.get("allProducers") instanceof LinkedHashMap) {
            filterList = (List<String>) ((LinkedHashMap) filter.get("allProducers")).keySet().stream().collect(Collectors.toList());
        }else if (filter.get("allProducers") instanceof ArrayList){
            filterList = (List<String>) filter.get("allProducers");
        }

        Integer max = (Integer) filter.get("max");
        Integer min = (Integer) filter.get("min");

        return ResponseEntity.ok(productsService.getSideBarFilterProducts(category, filterList, max, min).asMap());
    }
}