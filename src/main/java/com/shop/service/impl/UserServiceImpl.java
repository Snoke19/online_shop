package com.shop.service.impl;

import com.shop.dao.UserDAO;
import com.shop.dao.UserRoleDAO;
import com.shop.dto.user.AdminDTO;
import com.shop.dto.user.UserDTO;
import com.shop.dto.user.UserMapper;
import com.shop.entity.User;
import com.shop.entity.UserRole;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserRoleDAO userRoleDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public List<UserDTO> getAll() {
        return UserMapper.mapper.usersToUsersDTO(userDAO.getAll());
    }


    @Override
    @Transactional
    public UserDTO get(Long id) {
        return UserMapper.mapper.userToUserDTO(userDAO.get(id));
    }


    @Override
    @Transactional
    public void add(UserDTO entityDTO) {
        userDAO.add(UserMapper.mapper.userDTOToUser(entityDTO));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userDAO.delete(id);
    }


    @Override
    @Transactional
    public void update(UserDTO entityDTO) {
        userDAO.update(UserMapper.mapper.userDTOToUser(entityDTO));
    }


    @Override
    @Transactional
    public List<UserDTO> getAllUsers() {
        return UserMapper.mapper.usersToUsersDTO(userDAO.getAllUsers());
    }


    @Override
    @Transactional
    public List<AdminDTO> getAllAdmins() {
        List<AdminDTO> adminDTOList = UserMapper.mapper.usersToAdminsDTO(userDAO.getAllAdmins());
        Collections.reverse(adminDTOList);
        return adminDTOList;
    }


    @Override
    @Transactional
    public void updateEnabled(boolean data, Long id) {
        userDAO.updateEnabled(data, id);
    }


    @Override
    @Transactional
    public void addAdmin(AdminDTO adminDTO) {

        User user = UserMapper.mapper.AdminDTOToAdmin(adminDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDAO.add(user);

        UserRole userRole = new UserRole();
        userRole.setRole("ADMIN");

        userRole.setUser(user);

        userRoleDAO.add(userRole);
    }


    @Override
    @Transactional
    public boolean emailExist(String email) {
        User user = userDAO.findByEmail(email);
        return user != null;
    }


    @Override
    @Transactional
    public UserDTO findByEmail(String email) {
        return UserMapper.mapper.userToUserDTO(userDAO.findByEmail(email));
    }


    @Override
    @Transactional
    public UserRole findRolesForUser(User user) {
        return userDAO.findRolesForUser(user);
    }


    @Override
    public org.springframework.security.core.userdetails.User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return new org.springframework.security.core.userdetails.User(authentication.getName(),
                    "", authentication.getAuthorities());
        }
        return (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    }
}