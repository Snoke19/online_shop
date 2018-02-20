package com.shop.dao;

import com.shop.entity.OrderItems;
import com.shop.utils.layers.DAO;

import java.util.List;

public interface OrderItemsDAO extends DAO<OrderItems> {

    List<OrderItems> getOrdersHistoryByIdUser(Long id);

    List<OrderItems> getOrderItemsByIdUser(Long id);
}