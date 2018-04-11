package com.shop.controllers;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.shop.dto.product.ProductDTO;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Collection<ProductDTO>> allProductsByFilters(@PathVariable("category") String category, @RequestBody Map<String, Object> filter){
        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) filter.get("allFilter");
        @SuppressWarnings("unchecked")
        List<String> allProducers = (List<String>) filter.get("allProducers");

        Multimap<String, String> stringMap = filterList
                .stream()
                .map(elem -> elem.split(" "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        Set<ProductDTO> productDTOHashSet = new HashSet<>(productsService.getAllProductsByFilters(stringMap, allProducers, category));

        return ResponseEntity.status(HttpStatus.OK).body(productDTOHashSet);
    }


    @PutMapping("/producers/filtered/{category}")
    public ResponseEntity<Map<String, Long>> getAllProducerWithCountProductsByFilter(@PathVariable("category") String category, @RequestBody List<String> filter){

        Multimap<String, String> stringMap = filter
                .stream()
                .map(elem -> elem.split(" "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        return ResponseEntity.ok(productsService.getAllProducerWithCountProductsByFilter(stringMap, category));
    }


    @PutMapping("/products/price/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByPrice(@PathVariable("category") String category, @RequestBody Map<String, Object> filter){

        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) filter.get("allFilter");
        @SuppressWarnings("unchecked")
        List<String> allProducers = (List<String>) filter.get("allProducers");

        Integer min = (Integer) filter.get("min");
        Integer max = (Integer) filter.get("max");

        Multimap<String, String> stringMap = filterList
                .stream()
                .map(elem -> elem.split(" "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        System.out.println("filterList: " + filterList);
        System.out.println("allProducers: " + allProducers);
        System.out.println("min: " + min);
        System.out.println("max: " + max);
        System.out.println("filterList: " + filterList);
        System.out.println("Category: " + category);

        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProductsByPrice(stringMap, allProducers, category, max, min));
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