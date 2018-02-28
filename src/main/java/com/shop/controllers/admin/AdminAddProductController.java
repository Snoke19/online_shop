package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.category.CategoryDTO;
import com.shop.dto.product.ProductDTO;
import com.shop.service.ProductsService;
import com.shop.service.impl.ImageService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(productDTO.getCategory().getIdCategory());

        productDTO.setListImages(imageService.getListImages());

        productsService.add(productDTO);

        imageService.clearListImages();//after setting images in listImages we must clear image service list

        return ResponseEntity.status(HttpStatus.CREATED).body(new Gson().toJson("The product is added!"));
    }


    @GetMapping("/admin/products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAdminProducts());
    }


    @DeleteMapping("/admin/product")
    public ResponseEntity<String> deleteProduct(@RequestBody Long id) {
        productsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson("The product is deleted!"));
    }


    @DeleteMapping("/admin/products")
    public ResponseEntity<String> deleteProducts(@RequestBody String idProducts) {
        Type listId = new TypeToken<List<Long>>(){}.getType();
        productsService.deleteProductSelected(new Gson().fromJson(idProducts, listId));

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson("The products is deleted!"));
    }


    @PostMapping("/admin/upload/images")
    public ResponseEntity<Void> uploadImages(@RequestParam("file") MultipartFile file) throws IOException {
        imageService.addImage(file.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}