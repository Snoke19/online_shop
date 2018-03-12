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


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }


    @GetMapping("/admins")
    public ResponseEntity<List<AdminDTO>> getAllAdmins(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllAdmins());
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.get(id));
    }


    @GetMapping("/user/{id}/orders/history")
    public ResponseEntity<List<OrderItemsDTO>> getUserOrdersHistory(@PathVariable("id") Long id){
        List<OrderItemsDTO> orderItemsDTOList = ordersService.getOrdersHistoryByIdUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(orderItemsDTOList);
    }


    @PutMapping("/user/enabled/update")
    public ResponseEntity<String> updateEnabled(@RequestBody String json){

        JsonObject jos = new Gson().fromJson(json, JsonObject.class);
        Boolean data = jos.get("data").getAsBoolean();
        long id = jos.get("idUser").getAsLong();

        userService.updateEnabled(data, id);

        return ResponseEntity.ok(new Gson().toJson("Enabled is updated!"));
    }


    @PostMapping("/admin")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDTO adminDTO) {

        if (userService.emailExist(adminDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Gson().toJson("There is an account with that email address: " + adminDTO.getEmail()));
        }else {
            userService.addAdmin(adminDTO);
            return ResponseEntity.ok(new Gson().toJson("New admin is added!"));
        }
    }


    @DeleteMapping("/delete/admin")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAdmin(@RequestParam("idAdmin") Long id){
        userService.delete(id);
    }


    @DeleteMapping("/delete/user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam("idUser") Long id){
        userService.delete(id);
    }
}