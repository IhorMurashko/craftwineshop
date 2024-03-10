package com.craftWine.shop.service.userCartServcies;

import com.craftWine.shop.dto.userCartDTO.WineItemForUserCart;
import com.craftWine.shop.exceptions.UserNotFoundException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.UserCart;
import com.craftWine.shop.repositories.UserCartRepository;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Service
public class UserCartServiceImpl implements UserCartService {

    private final UserCartRepository userCartRepository;
    private final UserService userService;
    private final CraftWineService craftWineService;

    @Override
    public void save(UserCart userCart) {
        userCartRepository.save(userCart);
    }

    @Override
    public boolean addWineToTheCart(@NotNull String token, @NotNull WineItemForUserCart wineItemForUserCart) {

        Optional<User> optionalUser = userService.extractUserFromToken(token);
        Optional<CraftWine> optionalCraftWine = craftWineService.findById(wineItemForUserCart.wineId());


        if (optionalUser.isPresent() && optionalCraftWine.isPresent()) {

            User user = optionalUser.get();
            CraftWine craftWine = optionalCraftWine.get();

            user.getUserCart().getWinesWithQuantity()
                    .put(craftWine, wineItemForUserCart.quantity());

//            userService.saveUser(user);
            save(user.getUserCart());
            return true;
        } else {
            final String errorMessage =
                    optionalUser.isEmpty()
                            ? "Couldn't find user"
                            : "Couldn't find wine";


            throw new RuntimeException(errorMessage);
        }

    }


    @Override
    public boolean removeWineFromTheCart(@NotNull String token, @NotNull long wineId) {

        Optional<User> optionalUser = userService.extractUserFromToken(token);
        Optional<CraftWine> optionalCraftWine = craftWineService.findById(wineId);

        if (optionalUser.isPresent() && optionalCraftWine.isPresent()) {

            User user = optionalUser.get();
            CraftWine craftWine = optionalCraftWine.get();

            boolean userCartContainsAWine = user.getUserCart().getWinesWithQuantity().containsKey(craftWine);

            if (userCartContainsAWine) {
                user.getUserCart().getWinesWithQuantity().remove(craftWine);
//                userService.saveUser(user);
                save(user.getUserCart());
                return true;
            } else {
                throw new UserNotFoundException("Could not find wine in user cart");
            }


        }
        return false;
    }

    @Override
    public UserCart getCart(@NotNull String token) {

        return userService.extractUserFromToken(token).orElseThrow(() ->
                new UsernameNotFoundException("Could not find user")).getUserCart();
    }
}
