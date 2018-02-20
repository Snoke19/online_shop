package com.shop.dao;

import com.shop.dto.product.Description;
import com.shop.entity.Product;
import com.shop.utils.layers.DAO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsDAO extends DAO<Product> {

    List<Product> getAdminProducts();

    void updateNameProduct(String name, Long id);
    void updateProducer(String producer, Long id);
    void updateDescription(List<Description> desc, Long id);
    void updatePrice(BigDecimal price, Long id);
    void updateImage(List<byte[]> imageJson, Long id);
    void updateActive(boolean active, Long id);
    void updateQuantity(Integer number, Long id);
    void updateCategory(Long idCategory, Long id);
    void updateCode(String code, Long id);


    List<String> getAllProducer();
    List<String> getAllProducerByCategory(String nameCategory);

    List<Product> getAllProductsBySomething(String something);


    List<Object[]> getAllProductsWithCountProducts();
    List<Object[]> getAllProducerWithCountProducts();
    List<Object[]> getAllProducerWithCountProductsByCategory(String nameCategory);
    List<Object[]> getAllProductsWithCountProductsByProducer(String nameProducer);
}