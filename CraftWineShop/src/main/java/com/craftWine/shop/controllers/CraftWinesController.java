package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.exceptions.CraftWineNotFoundException;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Всі вина")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CraftWinesController {

    private final CraftWineService craftWineService;
    private final CraftWineMapper craftWineMapper;


    @Operation(
            summary = "колекція всіх вин",
            description = "кожен об'єкт репрезунтує вино, яке є на даний момент в магазині",

            responses = {@ApiResponse(
                    responseCode = "200", description = "статус ОК (200) та колекція (массив) об'єктів, де кожен репрезунтує вино"
            )}
    )
    @GetMapping(value = "/craft_wines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CraftWineDTOResponse>> allCraftWines() {

        List<CraftWine> craftWineList = craftWineService.findAll();


        List<CraftWineDTOResponse> craftWineDTOResponseList =
                craftWineList
                        .stream()
                        .map(craftWineMapper::toDTOResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(craftWineDTOResponseList);
    }

    @Operation(
            description = "отримати вино по унікальному ідентифікатору",
            method = "GET",
            responses = {@ApiResponse(responseCode = "200", description = "репрезентація знайденого вина")}
    )

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<CraftWineDTOResponse> getByID(
            @Parameter(description = "унікальний ідентифікатор вина")
            @PathVariable("id") long id) {

        Optional<CraftWine> optionalCraftWine = craftWineService.findById(id);

        if (optionalCraftWine.isPresent()) {

            CraftWineDTOResponse dtoResponse = craftWineMapper.toDTOResponse(optionalCraftWine.get());

            return ResponseEntity.ok(dtoResponse);

        } else {
            throw new CraftWineNotFoundException("Couldn't find wine");
        }
    }
}
