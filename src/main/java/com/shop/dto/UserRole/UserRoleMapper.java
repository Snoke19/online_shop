package com.shop.dto.UserRole;

import com.shop.dto.user.UserDTO;
import com.shop.entity.User;
import com.shop.entity.UserRole;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface UserRoleMapper {

    UserRoleMapper mapper = Mappers.getMapper(UserRoleMapper.class);

    List<UserRoleDTO> usersRoleToUsersRoleDTO(UserRole userRole);
    UserRoleDTO userRoleToUserRoleDTO(UserRole userRole);
    UserRole userRoleDTOToUserRole(UserRoleDTO userRoleDTO);
    UserDTO userToUserDTO(User user);
}
