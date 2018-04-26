package com.shop.controllers;


import com.shop.dto.user.LoginDTO;
import com.shop.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/authorize")
    public User authorize(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        usernamePasswordAuthenticationToken.setDetails(httpServletRequest);

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping("/current/user")
    public User currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new User(authentication.getName(), "", authentication.getAuthorities());
        }

        return (User) authentication.getPrincipal();
    }
}
