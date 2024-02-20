package com.craftWine.shop.dto.regionDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
public record NewRegionDTO(long producedCountryId,
                           String name) implements Serializable {
}
