package com.craftWine.shop.dto.regionDTO;

import java.io.Serializable;

public record NewRegionDTO(long producedCountryId,
                           String name) implements Serializable {
}
