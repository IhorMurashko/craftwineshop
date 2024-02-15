package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.regionDTO.RegionDTO;
import com.craftWine.shop.models.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    RegionDTO toDTO(Region region);


}
