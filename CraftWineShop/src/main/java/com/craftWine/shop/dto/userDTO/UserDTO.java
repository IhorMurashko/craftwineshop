package com.craftWine.shop.dto.userDTO;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.craftWine.shop.models.User}
 */
public record UserDTO(String email,
                      String firstName,
                      String lastName,
                      String deliveryAddress,
                      Set<CraftWineDTOResponse> favorites
) implements Serializable {
}