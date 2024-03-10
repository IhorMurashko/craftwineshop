package com.craftWine.shop.dto.userCartDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "сутність для додавання вина у кошик користувача")
public record WineItemForUserCart(
        @Schema(description = "унікальний ідентифікатор вина")
        long wineId,
        @Schema(description = "кількість пляшок")
        short quantity

) implements Serializable {
}
