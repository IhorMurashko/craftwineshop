package com.craftWine.shop.service.userFavoriteWineService;

import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserFavoriteWineServiceImpl implements UserFavoriteWineService {

    private final CraftWineService craftWineService;
    private final UserService userService;


    @Override
    public boolean addWineToFavorite(@NotNull String token, @NotNull long craftWineId) {

        User user = userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));

        CraftWine craftWine = craftWineService.findById(craftWineId)
                .orElseThrow(() -> new NotFoundException("Could not find craft with id " + craftWineId));

        if (user.getFavorites() == null) {
            user.setFavorites(new ArrayList<>(5));
        }

        user.getFavorites().add(craftWine);

        userService.saveUser(user);
        return true;
    }

    @Override
    public boolean removeWineFromFavorite(@NotNull String token, @NotNull long craftWineId) {

        User user = userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));

        CraftWine craftWine = craftWineService.findById(craftWineId)
                .orElseThrow(() -> new NotFoundException("Could not find craft with id " + craftWineId));

        boolean contains = user.getFavorites().contains(craftWine);
        if (contains) {
            user.getFavorites().remove(craftWine);
            return true;
        } else {
            throw new NotFoundException("Couldn't find wine");
        }
    }


}
