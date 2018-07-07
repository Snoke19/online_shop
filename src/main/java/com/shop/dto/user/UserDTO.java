package com.shop.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long idUser;
    private String userName;
    private String surname;
    private String email;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String phone;
    private Boolean enabled;
}