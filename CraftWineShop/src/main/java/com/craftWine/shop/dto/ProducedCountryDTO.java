package com.craftWine.shop.dto;

import com.craftWine.shop.models.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.craftWine.shop.models.ProducedCountry}
 */
@AllArgsConstructor
@Value
@Getter
public class ProducedCountryDTO implements Serializable {
    long id;
    String name;
}