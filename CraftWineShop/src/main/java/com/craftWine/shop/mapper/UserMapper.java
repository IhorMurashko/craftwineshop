package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.userDTO.UserDTO;
import com.craftWine.shop.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserCartMapper.class, CraftWineMapper.class})
public interface UserMapper {


    UserDTO toDTO(User user);


}