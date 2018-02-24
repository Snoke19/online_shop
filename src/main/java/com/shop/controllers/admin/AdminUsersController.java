package com.shop.controllers.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.dto.user.AdminDTO;
import com.shop.dto.user.UserDTO;
import com.shop.service.OrdersService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminUsersController {

    private UserService userService;
    private OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/get/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOList = userService.getAllUsers();

        if (userDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
        }
    }


    @GetMapping("/get/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins(){
        List<AdminDTO> adminDTOList = userService.getAllAdmins();

        if (adminDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(adminDTOList);
        }
    }


    @GetMapping("/get/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        if (userService.get(id) == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.get(id));
        }
    }


    @GetMapping("/get/user/{id}/orders/history")
    public ResponseEntity<List<OrderItemsDTO>> getUserOrdersHistory(@PathVariable("id") Long id){
        List<OrderItemsDTO> orderItemsDTOList = ordersService.getOrdersHistoryByIdUser(id);

        if (orderItemsDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(orderItemsDTOList);
        }
    }


    @PostMapping("/user/enabled/update")
    public void updateEnabled(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        Boolean data = jos.get("data").getAsBoolean();
        long id = jos.get("idUser").getAsLong();

        userService.updateEnabled(data, id);
    }


    @PostMapping(value = "/add/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAdmin(@RequestBody AdminDTO adminDTO) {

        if (userService.emailExist(adminDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Gson().toJson("There is an account with that email address: " + adminDTO.getEmail()));
        }else {
            userService.addAdmin(adminDTO);
            return ResponseEntity.ok().build();
        }
    }


    @DeleteMapping("/delete/admin")
    public ResponseEntity<Void> deleteAdmin(@RequestParam("idAdmin") Long id){
        boolean d =  userService.delete(id);

        if (d){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }


    @DeleteMapping("/delete/user")
    public ResponseEntity<Void> deleteUser(@RequestParam("idUser") Long id){
        boolean d =  userService.delete(id);

        if (d){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}