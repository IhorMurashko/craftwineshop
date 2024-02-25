package com.craftWine.shop.dto.userCartDTO;

import java.io.Serializable;

public record WineItemForUserCart(
        long wineId,
        short quantity

) implements Serializable {
}
