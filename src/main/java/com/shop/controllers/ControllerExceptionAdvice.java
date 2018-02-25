package com.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> acceptIllegalArgumentException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal argument exception: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<String> acceptHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        return ResponseEntity.status(BAD_REQUEST).body("HTTP message not readable exception: " + ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> acceptNoSuchElementException(final NoSuchElementException ex) {
        return ResponseEntity.status(NOT_FOUND).body("No such element exception: " + ex.getMessage());
    }



}
