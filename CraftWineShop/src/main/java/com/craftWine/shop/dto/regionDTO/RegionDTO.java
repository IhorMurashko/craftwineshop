package com.craftWine.shop.dto.regionDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link com.craftWine.shop.models.Region}
 */
@Schema(description = "Регіон виробництва вина")
public record RegionDTO(
        @Schema(description = "унікальний ідентифікатор регіону", examples = {"1", "74"})
        long id,
        @Schema(description = "Ім'я регіону", examples = {"Zakarpattia", " Kherson"})
        String name

) implements Serializable {
}