package com.craftWine.shop.dto.wineCommentDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record WineCommentRequestDTO(
        @Positive
        long wineId,
        @NotBlank(message = "comment cant be empty or blank")
        String userComment) {
}
