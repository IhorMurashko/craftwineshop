package com.craftWine.shop.dto.wineCommentDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record WineCommentRequestDTO(
        @Nullable
        long id,
        @NotBlank(message = "comment cant be empty or blank")
        String userComment) {
}
