package com.craftWine.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.craftWine.shop.models.Region}
 */
@AllArgsConstructor
@Value
@Getter
public class RegionDTO implements Serializable {
    long id;
    String name;
}