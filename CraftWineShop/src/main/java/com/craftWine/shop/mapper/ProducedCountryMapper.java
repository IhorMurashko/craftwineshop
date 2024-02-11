package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.regionDTO.RegionDTO;
import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryDTO;
import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryResponseWithSetRegionsDTO;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface ProducedCountryMapper {

    @Mapping(target = "regionDTOSet", source = "regions")
    ProducedCountryResponseWithSetRegionsDTO toProducedCountryResponseDTO(ProducedCountry producedCountry);

    ProducedCountryDTO toDTO(ProducedCountry producedCountry);

    Set<RegionDTO> toRegionDTOSet(Set<Region> regions);

}
