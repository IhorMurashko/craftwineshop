package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.userCartDTO.WineItemForUserCart;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.UserCart;
import com.craftWine.shop.service.userCartServcies.UserCartService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class UserCartController {

    private final UserCartService userCartService;
    private final CraftWineMapper craftWineMapper;


    @GetMapping(value = "/get_cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<CraftWineDTOResponse, Short> getCart(@RequestHeader(name = "Authorization") String headerToken) {

        UserCart cart = userCartService.getCart(headerToken);

        return cart.getWinesWithQuantity().entrySet()
                .stream().collect(Collectors.toMap(entry -> craftWineMapper.toDTOResponse(entry.getKey()), Map.Entry::getValue));
    }


    @PostMapping("/save_wine_to_cart")
    public ResponseEntity<HttpStatus> saveAWineToCart(@NotNull @RequestBody WineItemForUserCart wineItemForUserCart,
                                                      @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userCartService.addWineToTheCart(headerToken, wineItemForUserCart);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete_wine_from_the_cart/{wine_id}")
    public ResponseEntity<HttpStatus> deleteAWineFromCart(@NotNull @PathVariable("wine_id") long wineId,
                                                          @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userCartService.removeWineFromTheCart(headerToken, wineId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
