package com.shop.dto.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

    private Long idOrders;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String address;
    private String phone;
    private String status;
    private boolean delivery;
    private UserDTO user;
}