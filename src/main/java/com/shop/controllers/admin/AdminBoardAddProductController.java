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

@RestController
public class AdminBoardAddProductController {

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


    @GetMapping("/admin/products")
    public ResponseEntity<List<ProductDTO>> allProducts() {

        if (productsService.getAdminProducts().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(productsService.getAdminProducts());
        }
    }


    @DeleteMapping("/admin/product/delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody Long id) {
        if(productsService.delete(id)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @DeleteMapping("/admin/products/delete")
    public ResponseEntity<Void> deleteProducts(@RequestBody String idProducts) {
        Gson gson = new Gson();
        Type listId = new TypeToken<List<Long>>(){}.getType();
        productsService.deleteProductSelected(gson.fromJson(idProducts, listId));

        return ResponseEntity.ok().build();
    }


    @PostMapping("/admin/product/add")
    public ResponseEntity<Void> addProduct(@RequestBody ProductDTO productDTO) throws IOException {


        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(productDTO.getCategory().getIdCategory());

        productDTO.setListImages(imageService.getListImages());

        productsService.add(productDTO);

        imageService.clearListImages();

        return ResponseEntity.ok().build();
    }


    @PostMapping("/admin/upload/images")
    public ResponseEntity<Void> uploadImages(@RequestParam("file") MultipartFile file) throws IOException {
        imageService.addImage(file.getBytes());
        return ResponseEntity.ok().build();
    }
}