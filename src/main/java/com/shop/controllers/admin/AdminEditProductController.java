package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.product.Description;
import com.shop.service.ProductsService;
import com.shop.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> updateNameProduct(@PathVariable("id") Long id, @RequestBody String name){
        productsService.updateNameProduct(name, id);

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson("Name of the product is updated!"));
    }


    @PutMapping("/product/{id}/producer")
    public ResponseEntity<String> updateProducer(@PathVariable("id") Long id, @RequestBody String producer){
        productsService.updateProducer(producer, id);
        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson("Producer of the product is updated!"));
    }


    @PutMapping("/product/{id}/description")
    public  ResponseEntity<String> updateDescription(@PathVariable("id") Long id, @RequestBody List<Map<String, List<Description>>> descriptionList){
        System.out.println(descriptionList);
        productsService.updateDescription(descriptionList, id);

        return ResponseEntity.ok(new Gson().toJson("Description is updated!"));
    }


    @PutMapping("/product/{id}/price")
    public ResponseEntity<String> updatePrice(@PathVariable("id") Long id, @RequestBody BigDecimal price){
        productsService.updatePrice(price, id);
        return ResponseEntity.ok(new Gson().toJson("Price of the product is updated"));
    }


    @PutMapping("/product/{id}/quantity")
    public ResponseEntity<String> updateQuantity(@PathVariable("id") Long id, @RequestBody Integer quantity){
        productsService.updateQuantity(quantity, id);
        return ResponseEntity.ok(new Gson().toJson("Quantity of the product is updated!"));
    }


    @PutMapping("/product/{id}/active")
    public ResponseEntity<String> updateActive(@PathVariable("id") Long id, @RequestBody boolean active){
        productsService.updateActive(active, id);
        return ResponseEntity.ok(new Gson().toJson("Status of the product is updated"));
    }


    @PutMapping("/product/{id}/category")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long id, @RequestBody Long idCategory){
        productsService.updateCategory(idCategory, id);
        return ResponseEntity.ok(new Gson().toJson("Category of the product is updated"));
    }


    @PutMapping("/product/{id}/code")
    public ResponseEntity<String> updateCode(@PathVariable("id") Long id, @RequestBody String code){
        productsService.updateCode(code, id);
        return ResponseEntity.ok(new Gson().toJson("Code of the product is updated!"));
    }


    @DeleteMapping("/product/{id}/images/{index}")
    public ResponseEntity<String> deleteImageFromList(@PathVariable Long id, @PathVariable("index") int index) throws IOException {
        productsService.deleteOneImageProduct(index, id);

        return ResponseEntity.ok(new Gson().toJson("The image is removed!'"));
    }


    @PostMapping("/admin/{id}/images")
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