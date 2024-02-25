package com.craftWine.shop.service.userFavoriteWineService;

import jakarta.validation.constraints.NotNull;

public interface UserFavoriteWineService {

    boolean addWineToFavorite(@NotNull String token, @NotNull long craftWineId);

    boolean removeWineFromFavorite(@NotNull String token, @NotNull long craftWineId);

}
