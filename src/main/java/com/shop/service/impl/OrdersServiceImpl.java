package com.shop.service.impl;

import com.shop.dao.OrderItemsDAO;
import com.shop.dao.OrdersDAO;
import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.dto.orderItems.OrderItemsMapper;
import com.shop.dto.orders.OrdersDTO;
import com.shop.dto.orders.OrdersMapper;
import com.shop.entity.Orders;
import com.shop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

    private OrdersDAO ordersDAO;
    private OrderItemsDAO orderItemsDAO;

    @Autowired
    public void setOrdersDAO(OrdersDAO ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    @Autowired
    public void setOrderItemsDAO(OrderItemsDAO orderItemsDAO) {
        this.orderItemsDAO = orderItemsDAO;
    }

    @Override
    @Transactional
    public List<OrdersDTO> getAll() {
        return OrdersMapper.mapper.listOrdersDTO(ordersDAO.getAll());
    }


    @Override
    @Transactional
    public OrdersDTO get(Long id) {
        return OrdersMapper.mapper.ordersToOrdersDTO(ordersDAO.get(id));
    }


    @Override
    @Transactional
    public void add(OrdersDTO entity) {
        ordersDAO.add(OrdersMapper.mapper.ordersDTOToOrders(entity));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        ordersDAO.delete(id);
    }


    @Override
    @Transactional
    public void update(OrdersDTO entity) {
        ordersDAO.update(OrdersMapper.mapper.ordersDTOToOrders(entity));
    }


    @Override
    @Transactional
    public List<OrderItemsDTO> getOrdersHistoryByIdUser(Long id) {
        return OrderItemsMapper.mapper.OrdersHistoryUserToDTO(orderItemsDAO.getOrdersHistoryByIdUser(id));
    }


    @Override
    @Transactional
    public List<OrderItemsDTO> getOrderItemsByIdUser(Long id) {
        return OrderItemsMapper.mapper.orderItemsToOrdersItemsDTO(orderItemsDAO.getOrderItemsByIdUser(id));
    }


    @Override
    @Transactional
    public List<OrdersDTO> getNewOrders() {
        return OrdersMapper.mapper.listOrdersDTO(ordersDAO.getNewOrders());
    }


    @Override
    @Transactional
    public List<OrdersDTO> getOrdersByStatus(String status) {
        return OrdersMapper.mapper.listOrdersDTO(ordersDAO.getOrdersByStatus(status));
    }


    @Override
    @Transactional
    public void updateStatusOrder(String status, Long id) {
        ordersDAO.updateStatusOrder(status, id);
    }


    @Override
    @Transactional
    public Map<String, Integer> getNumberStatus() {
        List<Orders> ordersList = ordersDAO.getAll();
        List<String> status = ordersList.stream().map(Orders::getStatus).collect(Collectors.toList());

        Map<String, Integer> mapNumberStatus = new HashMap<>();

        status.forEach(s -> mapNumberStatus.put(s, Collections.frequency(status, s)));

        return mapNumberStatus;
    }
}