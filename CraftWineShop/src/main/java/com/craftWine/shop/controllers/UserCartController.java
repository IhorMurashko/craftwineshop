package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.userCartDTO.UserCartWithQuantityDTO;
import com.craftWine.shop.dto.userCartDTO.WineItemForUserCart;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.UserCartMapper;
import com.craftWine.shop.models.UserCart;
import com.craftWine.shop.service.userCartServcies.UserCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Кошик користувача")
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class UserCartController {

    private final UserCartService userCartService;
    private final CraftWineMapper craftWineMapper;
    private final UserCartMapper userCartMapper;

    @Operation(
            summary = "кошик користувача",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "словник у игляді об'єкта, " +
                            "який репрезунтує вина, кількість та цііну")
            }
    )

    @GetMapping(value = "/get_cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<UserCartWithQuantityDTO, BigDecimal>> getCart(
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            ) @RequestHeader(name = "Authorization") String headerToken) {

        UserCart cart = userCartService.getCart(headerToken);


        Map<UserCartWithQuantityDTO, BigDecimal> mapUserCartWithQuantityAndPrice = cart.getWinesWithQuantity().entrySet()
                .stream()
                .map(entry -> userCartMapper.toUserCartWithQuantityDto(
                        entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(
                        userCartWithQuantityDTO ->
                                userCartWithQuantityDTO,

                        userCartWithQuantityDTO ->
                                userCartWithQuantityDTO.craftWineDTOResponse().price()
                                        .multiply(BigDecimal.valueOf(
                                                userCartWithQuantityDTO.quantity()
                                        ))
                ));
        return ResponseEntity.ok(mapUserCartWithQuantityAndPrice);

    }

    @Operation(
            summary = "додати вино у кошик",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
            }
    )
    @PostMapping("/save_wine_to_cart")
    public ResponseEntity<HttpStatus> saveAWineToCart(
            @Parameter(required = true, description = "сутність для додавання вина у кошик, " +
                    "яка містить унікальний ідентифікатор вина та кількість пляшок")
            @NotNull @RequestBody WineItemForUserCart wineItemForUserCart,
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            )
            @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userCartService.addWineToTheCart(headerToken, wineItemForUserCart);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(
            description = "видалення вина із кошика",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "вдалення видалення"),
                    @ApiResponse(responseCode = "400", description = "помилка запиту"),

            }
    )
    @DeleteMapping("/delete_wine_from_the_cart/{wine_id}")
    public ResponseEntity<HttpStatus> deleteAWineFromCart(
            @Parameter(description = "унікальний ідентифікатор вина", required = true)
            @NotNull @PathVariable("wine_id") long wineId,
            @Parameter(description = "токен користувача в заголовці Authorization", required = true)
            @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userCartService.removeWineFromTheCart(headerToken, wineId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
