package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.category.CategoryDTO;
import com.shop.dto.product.ProductDTO;
import com.shop.service.ProductsService;
import com.shop.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class AdminAddProductController {

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


    @PostMapping("/admin/product")
    @ResponseStatus(HttpStatus.OK)
    public void addProduct(@RequestBody ProductDTO productDTO) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(productDTO.getCategory().getIdCategory());

        productDTO.setListImages(imageService.getListImages());

        productsService.add(productDTO);

        imageService.clearListImages();
    }


    @GetMapping("/admin/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> allProducts() {
        return productsService.getAdminProducts();
    }


    @DeleteMapping("/admin/product")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestBody Long id) {
        productsService.delete(id);
    }


    @DeleteMapping("/admin/products")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProducts(@RequestBody String idProducts) {
        Type listId = new TypeToken<List<Long>>(){}.getType();
        productsService.deleteProductSelected(new Gson().fromJson(idProducts, listId));
    }


    @PostMapping("/admin/upload/images")
    @ResponseStatus(HttpStatus.OK)
    public void uploadImages(@RequestParam("file") MultipartFile file) throws IOException {
        imageService.addImage(file.getBytes());
    }
}