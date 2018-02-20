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

        if (productsService.getAll().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAll());
        }
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<List<ProductDTO>> allProductsByCategory(@PathVariable("category") String category){

        if (productsService.getAllProductsBySomething(category).isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProductsBySomething(category));
        }

    }

    @GetMapping("/product/get/{idProduct}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("idProduct") Long idProduct){

        if (productsService.get(idProduct) == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.get(idProduct));
        }
    }

    @GetMapping("/producers")
    public ResponseEntity<List<String>> allProducer(){

        if (productsService.getAllProducer().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProducer());
        }
    }

    @GetMapping("/producers/{nameCategory}")
    public ResponseEntity<List<String>> getAllProducersByCategory(@PathVariable("nameCategory") String nameCategory){

        if (productsService.getAllProducerByCategory(nameCategory).isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProducerByCategory(nameCategory));
        }
    }
}