package com.shop.dao;

import com.shop.entity.User;
import com.shop.entity.UserRole;
import com.shop.utils.layers.DAO;

import java.util.List;

public interface UserDAO extends DAO<User> {

    List<User> getAllUsers();
    List<User> getAllAdmins();
    void updateEnabled(boolean data, Long id);

    User findByEmail(String email);
    UserRole findRolesForUser(User user);
}