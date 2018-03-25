package com.shop.controllers;

import com.shop.service.CategoryService;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/sidebar/producer/{nameCategory}")
    public Map<String, Long> getAllProducerWithCountProductsByCategory(@PathVariable("nameCategory") String nameCategory){
        return productsService.getAllProducerWithCountProductsByCategory(nameCategory);
    }

    @GetMapping("/sidebar/products/{nameProducer}")
    public Map<String, Long> getAllProductsWithCountProductsByProducer(@PathVariable("nameProducer") String nameProducer){
        return productsService.getAllProductsWithCountProductsByProducer(nameProducer);
    }

    @GetMapping("/categories/count/product")
    public Map<String, Long> getAllCategoriesWithCountProducts(){
        return categoryService.getAllCategoriesWithCountProducts();
    }

    @GetMapping("/sidebar/producer")
    public Map<String, Long> getAllProducerWithCountProducts(){
        return productsService.getAllProducerWithCountProducts();
    }

    @GetMapping("/sidebar/products")
    public Map<String, Long> getAllProductsWithCountProducts(){
        return productsService.getAllProductsWithCountProducts();
    }


}