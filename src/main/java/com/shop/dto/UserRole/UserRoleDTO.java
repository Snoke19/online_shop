package com.shop.dto.UserRole;

import com.shop.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {

    private Long idUserRole;
    private String role;
    private UserDTO user;
}
