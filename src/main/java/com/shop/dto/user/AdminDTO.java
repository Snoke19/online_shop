package com.shop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    private Long idUser;
    private String userName;
    private String surname;
    private String email;
    private String password;
    private Boolean enabled;
}
