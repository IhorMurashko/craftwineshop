package com.craftWine.shop.dto.producedCountryDTO;

import com.craftWine.shop.dto.RegionDTO;

import java.util.Set;

/**
 * DTO for {@link com.craftWine.shop.models.ProducedCountry}
 */
public record ProducedCountryResponseWithSetRegionsDTO(long id,
                                                       String name,
                                                       Set<RegionDTO> regionDTOSet) {
}