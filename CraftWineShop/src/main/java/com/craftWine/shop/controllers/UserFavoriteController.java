package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.userFavoriteWineService.UserFavoriteWineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "додати вино до обраного")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/favorites")
public class UserFavoriteController {

    private final UserFavoriteWineService userFavoriteWineService;
    private final CraftWineMapper craftWineMapper;

    @Operation(
            description = "всі обрані вина користувача",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "репрезентація вин, які присутні в обраному")
            }
    )
    @GetMapping(value = "/get_favorites", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Set<CraftWineDTOResponse>> getFavorites(
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            )
            @RequestHeader(name = "Authorization") String headerToken) {
        Set<CraftWine> favorites = userFavoriteWineService.getFavorites(headerToken);

        Set<CraftWineDTOResponse> favoritesDTO = favorites.stream()
                .map(craftWineMapper::toDTOResponse)
                .collect(Collectors.toSet());


        return ResponseEntity.ok(favoritesDTO);
    }


    @Operation(
            description = "додати вино до обраного",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "додано"),
                    @ApiResponse(responseCode = "400", description = "невдача")
            }
    )
    @GetMapping("/add_favorite/{craftWineId}")

    public ResponseEntity<HttpStatus> addFavorite(
            @Parameter(required = true, description = "унікальний ідентифікатор вина")
            @PathVariable(value = "craftWineId") long craftWineId,
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            )
            @RequestHeader(name = "Authorization") String headerToken) {


        boolean result = userFavoriteWineService.addWineToFavorite(headerToken, craftWineId);


        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(
            description = "видалити вино з обраного",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "вдале видлення"),
                    @ApiResponse(responseCode = "400", description = "невдача")
            }
    )
    @DeleteMapping("/delete_favorite/{craftWineId}")
    public ResponseEntity<HttpStatus> deleteFavorite(
            @Parameter(required = true, description = "унікальний ідентифікатор вина")
            @PathVariable("craftWineId") long craftWineId,
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            )
            @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userFavoriteWineService.removeWineFromFavorite(headerToken, craftWineId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(
            description = "видалити всі вина з обраного",
            method = "DELETE",
            responses = {
                    @ApiResponse(responseCode = "204", description = "вдале видлення"),
                    @ApiResponse(responseCode = "400", description = "невдача")
            }
    )
    @DeleteMapping("/delete_all_favorites")
    public ResponseEntity<HttpStatus> deleteAllFavorites(
            @Parameter(
                    required = true,
                    description = "токен користувача в заголовці Authorization"
            )
            @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userFavoriteWineService.removeAllFavorites(headerToken);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
