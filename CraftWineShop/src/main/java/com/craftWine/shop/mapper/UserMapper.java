package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.userDTO.UserDTO;
import com.craftWine.shop.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserCartMapper.class})
public interface UserMapper {

    UserDTO toDTO(User user);
}
