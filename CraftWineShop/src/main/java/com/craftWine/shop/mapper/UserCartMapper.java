package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.userCartDTO.UserCartWithQuantityDTO;
import com.craftWine.shop.models.CraftWine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CraftWineMapper.class})
public interface UserCartMapper {


    @Mapping(target = "craftWineDTOResponse", source = "craftWine")
    @Mapping(target = "quantity", source = "quantity")
    UserCartWithQuantityDTO toUserCartWithQuantityDto(CraftWine craftWine, short quantity);

}
