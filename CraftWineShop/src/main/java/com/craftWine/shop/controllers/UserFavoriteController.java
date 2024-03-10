package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.userFavoriteWineService.UserFavoriteWineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/favorites")
public class UserFavoriteController {

    private final UserFavoriteWineService userFavoriteWineService;
    private final CraftWineMapper craftWineMapper;


    @GetMapping(value = "/get_favorites", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Set<CraftWineDTOResponse>> getFavorites(@RequestHeader(name = "Authorization") String headerToken) {
        Set<CraftWine> favorites = userFavoriteWineService.getFavorites(headerToken);

        Set<CraftWineDTOResponse> favoritesDTO = favorites.stream()
                .map(craftWineMapper::toDTOResponse)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(favoritesDTO);
    }


    @GetMapping("/add_favorite/{craftWineId}")

    public ResponseEntity<HttpStatus> addFavorite(@PathVariable(value = "craftWineId") long craftWineId,
                                                  @RequestHeader(name = "Authorization") String headerToken) {


        boolean result = userFavoriteWineService.addWineToFavorite(headerToken, craftWineId);


        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete_favorite/{craftWineId}")
    public ResponseEntity<HttpStatus> deleteFavorite(@PathVariable("craftWineId") long craftWineId,
                                                     @RequestHeader(name = "Authorization") String headerToken) {

        boolean result = userFavoriteWineService.removeWineFromFavorite(headerToken, craftWineId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
