package com.shop.service;

import com.shop.entity.Product;
import com.shop.utils.SideBar;
import com.shop.utils.products.Description;
import com.shop.dto.product.ProductDTO;
import com.shop.utils.layers.Service;
import com.shop.utils.products.DescriptionCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductsService extends Service<ProductDTO> {

    List<ProductDTO> getAdminProducts();

    void updateNameProduct(String name, Long id);
    void updateProducer(String producer, Long id);
    void updateDescription(List<DescriptionCategory> desc, Long id);
    void updatePrice(BigDecimal price, Long id);
    void updateImage(List<byte[]> imageJson, Long id);
    void updateActive(boolean active, Long id);
    void updateQuantity(Integer number, Long id);
    void updateCategory(Long idCategory, Long id);
    void updateCode(String code, Long id);

    void deleteOneImageProduct(int indexImage, Long idProduct);

    void setDiscount(List<Long> idList, Integer discount);

    List<String> getAllProducer();
    List<String> getAllProducerByCategory(String nameCategory);

    List<ProductDTO> getProductsByRange(Integer start, String category);

    List<ProductDTO> getAllProductsByCategory(String something);

    Map<String, Long> getAllProductsWithCountProducts();
    Map<String, Long> getAllProducerWithCountProducts();
    Map<String, Long> getAllProducerWithCountProductsByCategory(String nameCategory);
    Map<String, Long> getAllProductsWithCountProductsByProducer(String nameProducer);

    void deleteProductSelected(List<Long> selected);

    List<Map<String, String>> getSideBarProducts();
}