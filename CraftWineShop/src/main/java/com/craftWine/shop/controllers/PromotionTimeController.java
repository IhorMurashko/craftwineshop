package com.craftWine.shop.controllers;

import com.craftWine.shop.service.promotionServices.WineTimePromotionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "акція \"час вина\"",
        description = "вина та країни, на які діє акція час вина")
@RestController
@RequestMapping("/api/v1/wine_time_promotion")
@RequiredArgsConstructor
public class PromotionTimeController {
    private final WineTimePromotionHandler wineTimePromotionHandler;

    @Operation(
            summary = "Час вина",
            description = "країни та вина, на які зараз діє акція \"час вина\"",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "массив, який містить массив " +
                            "країн та массив вин, на які зараз діє акція")
            }
    )

    @GetMapping("/get_promotion")
    public ResponseEntity<List<?>> getPromotionCountries() {

        List<?> promotions = wineTimePromotionHandler.getPromotions();
        return ResponseEntity.ok(promotions);
    }


}
