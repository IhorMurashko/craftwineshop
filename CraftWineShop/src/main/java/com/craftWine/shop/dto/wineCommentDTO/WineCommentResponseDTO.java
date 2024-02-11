package com.craftWine.shop.dto.wineCommentDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.craftWine.shop.models.WineComment}
 */
@Schema(description = "Сутність для кожного коментаря")
public record WineCommentResponseDTO(
        @Schema(description = "унікальний ідентифікатор коментаря", examples = {"7", "10"})
        long id,
        @Schema(description = "ім'я користувача, який залишив коментар", examples = {"Ігор", "Usuf"})
        String userFirstName,
        @Schema(description = "прізвище користувача, який залишив коментар", examples = {"Мурашко", "Gregorian"})
        String userLastName,
        @Schema(description = "коментар")
        String comment,
        @Schema(description = "дата додавання коментаря")
        LocalDateTime addedCommentTime) implements Serializable {

}