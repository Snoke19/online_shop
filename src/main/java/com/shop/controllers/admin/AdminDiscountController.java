package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Secured("ROLE_ADMIN")
public class AdminDiscountController {

    private ProductsService productsService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }


    @PutMapping("/products/{discount}")
    public ResponseEntity<String> setDiscount(@PathVariable("discount") Integer discount, @RequestBody List<Long> idList){

        productsService.setDiscount(idList, discount);

        return ResponseEntity.ok(new Gson().toJson("Discount was added"));
    }
}