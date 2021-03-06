package com.shop.controllers.admin;


import com.google.gson.Gson;
import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.dto.orders.OrdersDTO;
import com.shop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Secured("ROLE_ADMIN")
public class AdminOrdersUsersController {

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrdersDTO>> getNewOrders(){
        List<OrdersDTO> ordersDTOList = ordersService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDTOList);
    }


    @GetMapping("/orders/{status}")
    public ResponseEntity<List<OrdersDTO>> getOrdersByStatus(@PathVariable("status") String status){

        if (status.equals("all")){
            return ResponseEntity.status(HttpStatus.OK).body(ordersService.getAll());
        }

        return ResponseEntity.status(HttpStatus.OK).body(ordersService.getOrdersByStatus(status));
    }


    @GetMapping("/user/order/{id}/orderitems")
    public ResponseEntity<List<OrderItemsDTO>> getOrderByIdUser(@PathVariable("id") Long id) {
        List<OrderItemsDTO> orderItemsDTO = ordersService.getOrderItemsByIdUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderItemsDTO);
    }


    @GetMapping("/count/status/orders")
    public ResponseEntity<Map<String, Integer>> getCountStatusOrders(){
        return ResponseEntity.ok(ordersService.getNumberStatus());
    }


    @PutMapping("/order/{id}/update/{status}")
    public ResponseEntity<String> updateStatusOrder(@PathVariable("id") Long id, @PathVariable("status") String status){
        ordersService.updateStatusOrder(status, id);

        switch (status) {
            case "in process":
                return ResponseEntity.ok(new Gson().toJson("Order is admitted!"));
            case "in sent":
                return ResponseEntity.ok(new Gson().toJson("Order is sended!"));
            case "canceled":
                return ResponseEntity.ok(new Gson().toJson("Order is canceled!"));
        }

        return ResponseEntity.ok().build();
    }
}