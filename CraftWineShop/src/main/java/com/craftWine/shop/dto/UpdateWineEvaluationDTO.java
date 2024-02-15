package com.craftWine.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

@Schema(description = "сутність оцінки")
public record UpdateWineEvaluationDTO(
        @Schema(description = "унікальний ідентифікатор вина", examples = {"4", "5"})
        @Positive
        long wineId,
        @Schema(title = "оцінка від користовача для вина",
                description = "оцінка може бути не менше одиниці та не більше п'яти", examples = {"1", "4"},
                type = "integer", minimum = "1", maximum = "5")
        @Min(value = 1, message = "evaluation can't be less, than 1")
        @Max(value = 5, message = "evaluation can't be more, than 5")
        short evaluation) implements Serializable {


}
