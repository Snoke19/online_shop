package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.product.Description;
import com.shop.service.ProductsService;
import com.shop.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdminBoardEditProductController {

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


    @PostMapping(value = "/product/name/update")
    public void updateNameProduct(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        String nameUpdate = jos.get("name").getAsString();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateNameProduct(nameUpdate, id);
    }


    @PostMapping(value = "/product/producer/update")
    public void updateProducer(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        String producer = jos.get("producer").getAsString();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateProducer(producer, id);
    }


    @PostMapping(value = "/product/description/update")
    public void updateDescription(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        String desc = jos.get("json").getAsString();

        Type type = new TypeToken<List<Description>>() {}.getType();

        List<Description> description = new Gson().fromJson(desc, type);
        long id = jos.get("idProduct").getAsLong();

        productsService.updateDescription(description, id);
    }


    @PostMapping(value = "/product/price/update")
    public void updatePrice(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        BigDecimal price = jos.get("price").getAsBigDecimal();
        long id = jos.get("idProduct").getAsLong();

        productsService.updatePrice(price, id);
    }


    @DeleteMapping(value = "/product/image/update")
    public void deleteImageFromList(@RequestParam("index") String index,
                            @RequestParam("idProduct") String id) throws IOException {

        productsService.deleteOneImageProduct(Integer.parseInt(index), Long.parseLong(id));
    }


    @PostMapping("/admin/edit/upload/images")
    public void uploadImages(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) throws IOException {
        imageService.addImage(file.getBytes());

        List<byte[]> imagesUploaded = imageService.getListImages();
        List<byte[]> listImages = productsService.get(id).getListImages();
        listImages.addAll(imagesUploaded);

        imageService.clearListImages(); //MUST CLEAR LIST
        productsService.updateImage(listImages, id);
    }


    @PostMapping("/product/quantity/update")
    public void updateStatus(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        Integer quantity = jos.get("quantity").getAsInt();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateQuantity(quantity, id);
    }


    @PostMapping(value = "/product/active/update")
    public void updateActive(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        boolean active = jos.get("active").getAsBoolean();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateActive(active, id);
    }


    @PostMapping(value = "/product/category/update")
    public void updateCategory(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        Long category = jos.get("category").getAsLong();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateCategory(category, id);
    }


    @PostMapping(value = "/product/code/update")
    public void updateCode(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        String code = jos.get("code").getAsString();
        long id = jos.get("idProduct").getAsLong();

        productsService.updateCode(code, id);
    }
}