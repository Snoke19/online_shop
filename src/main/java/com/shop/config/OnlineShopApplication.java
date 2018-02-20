package com.shop.config;


import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
@ComponentScan("com.shop")
public class OnlineShopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OnlineShopApplication.class);
    }
}