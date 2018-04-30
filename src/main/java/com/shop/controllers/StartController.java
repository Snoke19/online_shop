package com.shop.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class StartController {

    @RequestMapping(value = "/")
    public String mainPage(){
        return "index";
    }

}