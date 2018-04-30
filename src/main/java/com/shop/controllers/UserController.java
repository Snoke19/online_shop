package com.shop.controllers;


import com.shop.dto.user.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@RestController
public class UserController {


    private  AuthenticationManager authenticationManager;


    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/authorize")
    public void authorize(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest){

        UsernamePasswordAuthenticationToken usernameToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        usernameToken.setDetails(httpServletRequest);

        Authentication authentication = authenticationManager.authenticate(usernameToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @GetMapping("/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!Objects.isNull(authentication)){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
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
