package com.shop.dto.orderItems;

import com.shop.dto.orders.OrdersDTO;
import com.shop.dto.product.ProductDTO;
import com.shop.entity.Orders;
import com.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTO {

    private Long idOrderItems;
    private Integer quantityProducts;
    private BigDecimal pricePerQuantity;
    private ProductDTO product;
    private OrdersDTO orders;
}