package com.craftWine.shop.dto.wineCommentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "сутність коментаря")
public record WineCommentRequestDTO(
        @Schema(description = "унікальний ідентифікатор вина", examples = {"4", "5"})
        @Positive
        long wineId,
        @Schema(description = "коментар від авторизованого користовача; не може бути порожнім", type = "String", examples = {"this is a good wine",
                "це вино гідне своєї вартості"} )
        @NotBlank(message = "comment cant be empty or blank")
        String userComment) {
}
