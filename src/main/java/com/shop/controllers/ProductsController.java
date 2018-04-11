package com.shop.controllers;

import com.shop.dto.product.ProductDTO;
import com.shop.service.ProductsService;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

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
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProductsByCategory(category));
    }

    @PutMapping("/products/filtered/{category}")
    public ResponseEntity<Collection<ProductDTO>> allProductsByFilters(@PathVariable("category") String category,
                                                                       @RequestBody Map<String, Object> filter){
        List<String> filterList = (List<String>) filter.get("allFilter");
        List<String> allProducers = (List<String>) filter.get("allProducers");

        Set<ProductDTO> productDTOHashSet = new HashSet<>(productsService.getAllProductsByFilters(filterList, allProducers, category));

        return ResponseEntity.status(HttpStatus.OK).body(productDTOHashSet);
    }


    @PutMapping("/producers/filtered/{category}")
    public ResponseEntity<Map<String, Long>> getAllProducerWithCountProductsByFilter(@PathVariable("category") String category, @RequestBody List<String> filter){
        return ResponseEntity.ok(productsService.getAllProducerWithCountProductsByFilter(filter, category));
    }


    @GetMapping("/more/products/{category}/{number}")
    public ResponseEntity<List<ProductDTO>> productsWithinRage(@PathVariable("category") String category, @PathVariable("number") Integer start){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProductsByRange(start, category));
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long idProduct){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.get(idProduct));
    }

    @GetMapping("/producers")
    public ResponseEntity<Map<String, Long>> getAllProducers(){
        return ResponseEntity.ok().body(productsService.getAllProducerWithCountProducts());
    }
}