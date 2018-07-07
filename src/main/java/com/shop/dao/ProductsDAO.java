package com.shop.dao;

import com.shop.entity.Product;
import com.shop.utils.layers.DAO;
import com.shop.utils.products.DescriptionCategory;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsDAO extends DAO<Product> {

    List<Product> getAdminProducts();

    void updateNameProduct(String name, Long id);
    void updateProducer(String producer, Long id);
    void updateDescription(List<DescriptionCategory> desc, Long id);
    void updatePrice(BigDecimal price, Long id);
    void updateImage(List<byte[]> imageJson, Long id);
    void updateActive(boolean active, Long id);
    void updateQuantity(Integer number, Long id);
    void updateCategory(Long idCategory, Long id);
    void updateCode(String code, Long id);

    void setDiscount(Long idProduct, Integer discount);

    List<Product> getProductsByRange(Integer start, String category);

    List<Product> getAllProductsByCategory(String something);

    List<Object[]> getAllProducerWithCountProducts();

    List getAllProductsProfile(String userEmail);
}