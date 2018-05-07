package com.shop.controllers;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.shop.dto.product.ProductDTO;
import com.shop.entity.Product;
import com.shop.service.ProductsService;
import com.shop.service.UserService;
import com.shop.utils.products.CountRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class ProductsController {

    private ProductsService productsService;
    private UserService userService;
    private CountRating countRating;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCountRating(CountRating countRating) {
        this.countRating = countRating;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> allProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAll());
    }


    @GetMapping("/products/{category}")
    public ResponseEntity<List<ProductDTO>> allProductsByCategory(@PathVariable("category") String category){

        productsService.getAllProductsByCategory(category).forEach(productDTO -> System.out.println(productDTO.getName()));

        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProductsByCategory(category));
    }


    @PutMapping("/products/filtered/{category}")
    public ResponseEntity<Set<ProductDTO>> allProductsByFilters(@PathVariable("category") String category,
                                                                @RequestBody Map<String, Object> filter){
        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) filter.get("allFilter");
        @SuppressWarnings("unchecked")
        List<String> allProducers = (List<String>) filter.get("allProducers");

        Multimap<String, String> stringMap = filterList
                .stream()
                .map(elem -> elem.split(": "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        Integer maxValue = (Integer) filter.get("max");
        Integer minValue = (Integer) filter.get("min");


        Set<ProductDTO> productDTOHashSet = new HashSet<>(productsService.getAllProductsByFilters(stringMap, allProducers, category, maxValue, minValue));

        return ResponseEntity.status(HttpStatus.OK).body(productDTOHashSet);
    }


    @PutMapping("/producers/filtered/{category}")
    public ResponseEntity<Map<String, Long>> getAllProducerWithCountProductsByFilter(@PathVariable("category") String category,
                                                                                     @RequestBody Map<String, Object> filter){

        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) filter.get("allFilter");

        Integer min = (Integer) filter.get("min");
        Integer max = (Integer) filter.get("max");

        Multimap<String, String> stringMap = filterList
                .stream()
                .map(elem -> elem.split(": "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        return ResponseEntity.ok(productsService.getAllProducerWithCountProductsByFilter(stringMap, category, max, min));
    }


    @PutMapping("/products/price/{category}")
    public ResponseEntity<Collection<ProductDTO>> getAllProductsByPrice(@PathVariable("category") String category,
                                                                        @RequestBody Map<String, Object> filter){

        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) filter.get("allFilter");
        @SuppressWarnings("unchecked")
        List<String> allProducers = (List<String>) filter.get("allProducers");

        Integer min = (Integer) filter.get("min");
        Integer max = (Integer) filter.get("max");

        Multimap<String, String> stringMap = filterList
                .stream()
                .map(elem -> elem.split(": "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));

        Set<ProductDTO> productDTOS = new HashSet<>(productsService.getAllProductsByPrice(stringMap, allProducers, category, max, min));

        return ResponseEntity.status(HttpStatus.OK).body(productDTOS);
    }


    @PutMapping("/more/products/{category}/{number}")
    public ResponseEntity<List<ProductDTO>> productsWithinRage(@PathVariable("category") String category,
                                                           @PathVariable("number") Integer start,
                                                           @RequestBody Map<String, Object> map){
        @SuppressWarnings("unchecked")
        List<String> filterList = (List<String>) map.get("allFilter");
        @SuppressWarnings("unchecked")
        List<String> allProducers = (List<String>) map.get("allProducers");

        Integer min = (Integer) map.get("min");
        Integer max = (Integer) map.get("max");

        Multimap<String, String> filterMap = filterList
                .stream()
                .map(elem -> elem.split(": "))
                .collect(ImmutableListMultimap.toImmutableListMultimap(e -> e[0], e -> e[1]));


        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProductsByRange(start, category, filterMap, allProducers, max, min));
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long idProduct){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.get(idProduct));
    }


    @GetMapping("/producers")
    public ResponseEntity<Map<String, Long>> getAllProducers(){
        return ResponseEntity.ok().body(productsService.getAllProducerWithCountProducts());
    }


    @PutMapping("/rating")
    public ResponseEntity<String> makeRating(@RequestBody Map<String, Object> map){
        Integer stars = (Integer) map.get("stars");
        String user = (String) map.get("email");
        Integer idProduct = (Integer) map.get("idProduct");

        if (userService.getCurrentUser().getUsername().equalsIgnoreCase(countRating.getCurrentUserInVotedRating(idProduct))){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new Gson().toJson("You have already voted! Your vote is not submitted!"));
        }

        return ResponseEntity.ok(String.valueOf(productsService.makeRating(Double.valueOf(stars), user, Long.valueOf(idProduct))));
    }
}