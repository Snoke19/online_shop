package com.shop.controllers;

import com.shop.dto.product.ProductDTO;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {

    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> allProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAll());
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<List<ProductDTO>> allProductsByCategory(@PathVariable("category") String category){

        if (productsService.getAllProductsBySomething(category).isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProductsBySomething(category));
        }

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long idProduct){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.get(idProduct));
    }
}