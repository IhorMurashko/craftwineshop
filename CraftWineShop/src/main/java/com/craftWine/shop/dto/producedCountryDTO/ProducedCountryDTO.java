package com.craftWine.shop.dto.producedCountryDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * DTO for {@link com.craftWine.shop.models.ProducedCountry}
 */
@Schema(description = "Країна виробник вина")
public record ProducedCountryDTO(
        @Schema(description = "унікальний ідентифікатор країни", examples = {"1", "74"})
        long id,
        @Schema(description = "Ім'я країни", examples = {"Ukraine", "Poland"})
        String name
) implements Serializable {
}