package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.ProducedCountryDTO;
import com.craftWine.shop.models.ProducedCountry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducedCountryMapper {

    ProducedCountryDTO toDTO(ProducedCountry producedCountry);

}
