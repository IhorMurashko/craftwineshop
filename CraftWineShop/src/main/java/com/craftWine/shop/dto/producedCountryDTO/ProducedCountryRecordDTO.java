package com.craftWine.shop.dto.producedCountryDTO;

import com.craftWine.shop.dto.RegionDTO;

import java.util.Set;

public record ProducedCountryRecordDTO(long id,
                                       String name,
                                       RegionDTO region) {
}
