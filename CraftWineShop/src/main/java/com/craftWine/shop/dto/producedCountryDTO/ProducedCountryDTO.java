package com.craftWine.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.craftWine.shop.models.ProducedCountry}
 */
public record ProducedCountryDTO(long id, String name) implements Serializable {
}