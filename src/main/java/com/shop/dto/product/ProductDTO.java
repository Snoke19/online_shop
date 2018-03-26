package com.shop.dto.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.shop.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long idProduct;
    private String name;
    private String producer;
    private List<Map<String, List<Description>>> description;
    private BigDecimal price;
    private List<byte[]> listImages;
    private Boolean isActive;
    private String code;
    private Integer quantity;
    private Integer discount;
    private Double ratings;
    private CategoryDTO category;
}