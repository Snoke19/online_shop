package com.shop.controllers;

import com.shop.dto.user.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;


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
}
