package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.product.Description;
import com.shop.service.ProductsService;
import com.shop.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdminEditProductController {

    private ProductsService productsService;
    private ImageService imageService;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }


    @PutMapping("/product/{id}/name")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateNameProduct(@PathVariable("id") Long id, @RequestBody String name){
        productsService.updateNameProduct(name, id);
    }


    @PutMapping("/product/{id}/producer")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProducer(@PathVariable("id") Long id, @RequestBody String producer){
        productsService.updateProducer(producer, id);
    }


    @PutMapping("/product/{id}/description")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateDescription(@PathVariable("id") Long id, @RequestBody List<Description> descriptionList){
        productsService.updateDescription(descriptionList, id);
    }


    @PutMapping("/product/{id}/price")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePrice(@PathVariable("id") Long id, @RequestBody BigDecimal price){
        productsService.updatePrice(price, id);
    }


    @PutMapping("/product/{id}/quantity")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateQuantity(@PathVariable("id") Long id, @RequestBody Integer quantity){
        productsService.updateQuantity(quantity, id);
    }


    @PutMapping("/product/{id}/active")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateActive(@PathVariable("id") Long id, @RequestBody boolean active){
        productsService.updateActive(active, id);
    }


    @PutMapping("/product/{id}/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCategory(@PathVariable("id") Long id, @RequestBody Long idCategory){
        productsService.updateCategory(idCategory, id);
    }


    @PutMapping("/product/{id}/code")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateCode(@PathVariable("id") Long id, @RequestBody String code){
        productsService.updateCode(code, id);
    }


    @DeleteMapping("/product/{id}/images/{index}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImageFromList(@PathVariable Long id, @PathVariable("index") int index) throws IOException {
        productsService.deleteOneImageProduct(index, id);
    }


    @PutMapping("/admin/{id}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImages(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        imageService.addImage(file.getBytes());

        List<byte[]> imagesUploaded = imageService.getListImages();
        List<byte[]> listImages = productsService.get(id).getListImages();
        listImages.addAll(imagesUploaded);

        imageService.clearListImages(); //MUST CLEAR LIST
        productsService.updateImage(listImages, id);
    }
}