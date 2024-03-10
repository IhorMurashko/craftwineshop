package com.craftWine.shop.service.userFavoriteWineService;

import com.craftWine.shop.exceptions.FavoriteEmptyListException;
import com.craftWine.shop.exceptions.UserNotFoundException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
                .orElseThrow(() -> new UserNotFoundException("Could not find craft with id " + craftWineId));

        if (user.getFavorites() == null) {
            user.setFavorites(new HashSet<CraftWine>(6, 0.75f));
        }

        user.getFavorites().add(craftWine);

        userService.saveUser(user);
        return true;
    }


    @Override
    public boolean removeAllFavorites(String token) {

        User user = userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));

        if (user.getFavorites().isEmpty()) {
            throw new FavoriteEmptyListException
                    ("your cart of favorites wines is empty");
        }

        user.getFavorites().iterator().remove();

        userService.saveUser(user);

        return true;
    }

    @Override
    public boolean removeWineFromFavorite(@NotNull String token, @NotNull long craftWineId) {

        User user = userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));

        CraftWine craftWine = craftWineService.findById(craftWineId)
                .orElseThrow(() -> new UserNotFoundException("Could not find craft with id " + craftWineId));

        boolean contains = user.getFavorites().contains(craftWine);
        if (contains) {
            user.getFavorites().remove(craftWine);
            userService.saveUser(user);
            return true;
        } else {
            throw new UserNotFoundException("Couldn't find wine in favorites");
        }
    }

    @Override
    public Set<CraftWine> getFavorites(@NotNull String token) {
        User user = userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user"));

        return user.getFavorites();
    }


}
