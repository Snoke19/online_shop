package com.shop.controllers;


import com.shop.dto.ProfileProducts;
import com.shop.dto.user.UserDTO;
import com.shop.service.ProductsService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    private ProductsService productsService;
    private UserService userService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current/user")
    public User currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new User(authentication.getName(), "", authentication.getAuthorities());
        }

        return (User) authentication.getPrincipal();
    }

    @GetMapping("/user/orders/profile")
    public ResponseEntity<List<ProfileProducts>> getAllOrdersUserProfile() {
        return ResponseEntity.ok(productsService.getAllProductsProfile(userService.getCurrentUser().getUsername()));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(userService.findByEmail(userService.getCurrentUser().getUsername()));
    }


    @PutMapping("/user/{idUser}/address")
    public ResponseEntity<String> updateUserAddress(@PathVariable("idUser") Long id, @RequestBody String newAddress){


    }
}