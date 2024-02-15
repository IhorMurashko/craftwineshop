package com.craftWine.shop.dto.producedCountryDTO;


import com.craftWine.shop.dto.regionDTO.RegionDTO;

public record ProducedCountryRecordDTO(long id,
                                       String name,
                                       RegionDTO region) {
}
