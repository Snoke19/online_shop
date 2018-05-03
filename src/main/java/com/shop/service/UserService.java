package com.shop.service;

import com.shop.dto.user.AdminDTO;
import com.shop.dto.user.UserDTO;
import com.shop.entity.User;
import com.shop.entity.UserRole;
import com.shop.utils.layers.Service;

import java.util.List;


public interface UserService extends Service<UserDTO> {

    List<UserDTO> getAllUsers();
    List<AdminDTO> getAllAdmins();
    void updateEnabled(boolean data, Long id);
    void addAdmin(AdminDTO adminDTO);
    boolean emailExist(String email);

    User findByEmail(String email);
    UserRole findRolesForUser(User user);

    org.springframework.security.core.userdetails.User getCurrentUser();
}