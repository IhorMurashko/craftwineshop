package com.craftWine.shop.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.craftWine.shop.models.Region}
 */
public record RegionDTO(
        long id
        , String name
) implements Serializable {
}