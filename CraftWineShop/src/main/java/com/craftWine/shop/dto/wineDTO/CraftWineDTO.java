package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.models.Region;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
public record CraftWineDTO(
        @NotBlank String wineName,
        @Digits(integer = 10, fraction = 2)
        @Positive
        BigDecimal price,
        @Digits(integer = 3, fraction = 2)
        @Min(0) @Max(100)
        float discount,
        @NotBlank String wineDescription,
        @PositiveOrZero short quantity,
        @Pattern(regexp = "^[0-9](\\.[0-9]{1,2})?$")
        String bottleCapacity,
        @Pattern(regexp = "^[0-9]{1,2}(\\.[0-9]{1,2})?$")
        String alcohol,
        @NotNull boolean isNewCollection,
        @NotNull boolean isBestSeller,
        @NotNull boolean isSale,
        @NotBlank String winemaking,
        @NotBlank String grapeVarieties,
        @NotBlank String tastingNotes,
        @NotBlank String storeAndServeAdvices,
        @NotBlank String foodPairing,
        @NotBlank String reviewsAndAwards,
        @NotBlank String wineColor,
        @NotBlank String sugarConsistency,
//        @NotNull ProducedCountry country,
//        @NotNull ProducedCountryDTO country,
        @NotNull Region region,
//        @NotNull RegionDTO region,
        @Nullable Long id,
        @Nullable String imageUrl) implements Serializable {

}