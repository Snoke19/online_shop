package com.shop.service;

import com.google.common.collect.Multimap;
import com.shop.dto.product.ProductDTO;
import com.shop.entity.Product;
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

    void deleteProductSelected(List<Long> selected);

    void setDiscount(List<Long> idList, Integer discount);

    List<ProductDTO> getProductsByRange(Integer start, String category);

    List<ProductDTO> getAllProductsByCategory(String something);

    Map<String, Long> getAllProducerWithCountProducts();

    Multimap<String, Map<String, Integer>> getSideBarFilterProducts(String category, List<String> producers, Integer max, Integer min);

    Map<String, Long> getAllProducerWithCountProductsByFilter(Multimap<String, String> filter, String category);

    List<ProductDTO> getAllProductsByFilters(Multimap<String, String> filters,
                                             List<String> producers,
                                             String category,
                                             Integer max,
                                             Integer min);

    List<ProductDTO> getAllProductsByPrice(Multimap<String, String> filters,
                                           List<String> producers,
                                           String category,
                                           Integer max,
                                           Integer min);
}