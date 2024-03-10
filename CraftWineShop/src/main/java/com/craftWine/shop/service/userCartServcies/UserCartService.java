package com.craftWine.shop.service.userCartServcies;

import com.craftWine.shop.dto.userCartDTO.WineItemForUserCart;
import com.craftWine.shop.models.UserCart;

public interface UserCartService {
    void save(UserCart userCart);


    boolean addWineToTheCart(String token, WineItemForUserCart wineItemForUserCart);

    boolean removeWineFromTheCart(String token, long wineID);

    UserCart getCart(String headerToken);
}
