package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 8753008929416174797L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_items")
    private Long idOrderItems;

    @Column(name = "quantity_products")
    private Integer quantityProducts;

    @Column(name = "price_per_quantity")
    private BigDecimal pricePerQuantity;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_products", nullable = false)
    private Product product;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_orders", nullable = false)
    private Orders orders;
}