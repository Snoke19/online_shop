package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileProducts {

    Integer pricePerQuantity;
    Integer quantityProducts;
    Double sum;
    String productName;
    String status;
}
