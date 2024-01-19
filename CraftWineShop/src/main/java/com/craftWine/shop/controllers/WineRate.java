package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.UpdateWineRateDTO;
import com.craftWine.shop.service.WineStarService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
@Validated
public class WineRate {

    private final WineStarService wineStarService;

    @Hidden
    @PostMapping(value = "/new/{wineName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateWineRate(@Valid @RequestBody UpdateWineRateDTO updateWineRateDTO,
                                                     @RequestHeader(name = "Authorization") String headerToken,
                                                     @PathVariable("wineName") String wineName) {


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();

        boolean result = false;

        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            String token = headerToken.substring(7);
            result = wineStarService.addRateForTheWine(token, updateWineRateDTO.wineId(), updateWineRateDTO.rate());

        }

        return result ?
                ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
