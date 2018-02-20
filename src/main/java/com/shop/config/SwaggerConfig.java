package com.shop.config;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shop.controllers"))
                .paths(regex("/*.*"))
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        return new ApiInfo(
                "Online Shop",
                "Spring Boot REST API for Online Shop",
                "1.0",
                "Terms of service",
                new Contact("", "https://springframework.guru/about/", "olexander0620@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }
}