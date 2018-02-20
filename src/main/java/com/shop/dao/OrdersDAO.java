package com.shop.dao;

import com.shop.entity.Orders;
import com.shop.utils.layers.DAO;

import java.util.List;

public interface OrdersDAO extends DAO<Orders> {

    List<Orders> getNewAndProcessOrders();

    void updateStatusOrder(String status, Long id);
}