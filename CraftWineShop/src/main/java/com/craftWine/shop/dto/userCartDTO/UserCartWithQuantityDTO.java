package com.craftWine.shop.dto.userCartDTO;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "сутність для відображення вина у кошику користувача та кількість пляшок")
public record UserCartWithQuantityDTO(
        @Schema(description = "репрезентація вина")
        CraftWineDTOResponse craftWineDTOResponse,
        @Schema(description = "кількість пляшок")
        short quantity
) {
}
