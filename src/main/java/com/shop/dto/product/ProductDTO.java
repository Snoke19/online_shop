package com.shop.dto.product;

import com.shop.dto.category.CategoryDTO;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
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
    private List<DescriptionCategory> description;
    private BigDecimal price;
    private List<byte[]> listImages;
    private Boolean isActive;
    private String code;
    private Integer quantity;
    private Integer discount;
    private Double ratings;
    private CategoryDTO category;
}