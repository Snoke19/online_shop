package com.shop.dto.user;

import com.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    List<UserDTO> usersToUsersDTO(List<User> userList);

    List<AdminDTO> usersToAdminsDTO(List<User> userList);

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

    @Mapping(target = "password", ignore = true)
    AdminDTO adminToAdminDTO(User user);

    User AdminDTOToAdmin(AdminDTO adminDTO);
}