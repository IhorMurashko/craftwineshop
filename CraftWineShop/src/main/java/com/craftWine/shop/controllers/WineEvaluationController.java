package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.UpdateWineEvaluationDTO;
import com.craftWine.shop.service.wineRateServices.WineEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "оцінка користувача для вина")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/evaluation")
@Validated
public class WineEvaluationController {

    private final WineEvaluationService wineEvaluationService;

    @Operation(
            description = "нова оцінка для вина від авторизованого користувача або оновлення існуючої",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "при успішній операції"),
                    @ApiResponse(responseCode = "400", description = "при неудачі")
            }
    )
    @PostMapping(value = "/new_evaluation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateWineEvaluation(
            @Parameter(description = "сутність для оцінки, яка містить інформацію про ідентифікатор вина та саму оцінку," +
                    " і проходить валідацію",
                    required = true)
            @Valid @RequestBody UpdateWineEvaluationDTO updateWineEvaluationDTO,

            @Parameter(description = "jwt token авторизованого користувача, який знаходить в header Authorization",
                    required = true)
            @RequestHeader(name = "Authorization") String headerToken) {


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();

        boolean result = false;

        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            String token = headerToken.substring(7);
            result = wineEvaluationService.addRateForTheWine(token, updateWineEvaluationDTO.wineId(), updateWineEvaluationDTO.evaluation());

        }

        return result ?
                ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
