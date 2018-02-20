package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.dto.orders.OrdersDTO;
import com.shop.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminOrdersUsersController {

    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/new/orders")
    public ResponseEntity<List<OrdersDTO>> getNewOrders(){
        List<OrdersDTO> ordersDTOList = ordersService.getNewOrders();
        return ResponseEntity.status(HttpStatus.OK).body(ordersDTOList);
    }

    @GetMapping("/get/user/orderitems/{id}")
    public ResponseEntity<List<OrderItemsDTO>> getOrderByIdUser(@PathVariable("id") Long id) {
        List<OrderItemsDTO> orderItemsDTO = ordersService.getOrderItemsByIdUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderItemsDTO);
    }

    @PostMapping("/order/update/status")
    public ResponseEntity<Void> updateStatusOrder(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        String status = jos.get("status").getAsString();
        long id = jos.get("idOrder").getAsLong();

        ordersService.updateStatusOrder(status, id);

        return ResponseEntity.ok().build();
    }
}