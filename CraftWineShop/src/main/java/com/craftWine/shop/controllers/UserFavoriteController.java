package com.craftWine.shop.controllers;

import com.craftWine.shop.service.userFavoriteWineService.UserFavoriteWineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/user_favorite")
public class UserFavoriteController {

    private final UserFavoriteWineService userFavoriteWineService;


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
