package com.craftWine.shop.service.userFavoriteWineService;

import com.craftWine.shop.models.CraftWine;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public interface UserFavoriteWineService {

    boolean addWineToFavorite(@NotNull String token, @NotNull long craftWineId);


    boolean removeAllFavorites(@NotNull String token);

    boolean removeWineFromFavorite(@NotNull String token, @NotNull long craftWineId);


    Set<CraftWine> getFavorites(String token);
}
