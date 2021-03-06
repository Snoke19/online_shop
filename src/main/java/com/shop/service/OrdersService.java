package com.shop.service;

import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.dto.orders.OrdersDTO;
import com.shop.entity.OrderItems;
import com.shop.entity.Orders;
import com.shop.utils.layers.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface OrdersService extends Service<OrdersDTO> {

    List<OrderItemsDTO> getOrdersHistoryByIdUser(Long id);

    List<OrderItemsDTO> getOrderItemsByIdUser(Long id);

    List<OrdersDTO> getNewOrders();

    List<OrdersDTO> getOrdersByStatus(String status);

    void updateStatusOrder(String status, Long id);

    Map<String, Integer> getNumberStatus();
}