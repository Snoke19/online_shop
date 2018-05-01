package com.shop.utils.security;

import com.shop.entity.UserRole;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserRole userRolesDTO = userService.findRolesForUser(userService.findByEmail(username));


        User.UserBuilder userBuilder;

        if (userRolesDTO != null){
            userBuilder = User.withUsername(username);
            userBuilder.password(passwordEncoder.encode(userRolesDTO.getUser().getPassword()));
            userBuilder.roles(userRolesDTO.getRole());
        }else {
            throw new UsernameNotFoundException("User not found.");
        }

        return userBuilder.build();
    }
}
