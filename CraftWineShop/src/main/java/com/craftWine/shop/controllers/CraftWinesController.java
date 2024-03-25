package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.data.DataDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.dto.wineDTO.CraftWineRegistrationDTO;
import com.craftWine.shop.exceptions.CraftWineNotFoundException;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.repositories.RegionRepository;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
    private final RegionRepository regionRepository;


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

    @Hidden
    @GetMapping("/generated_wines")
    public ResponseEntity<HttpStatus> generated_wines() {
        List<CraftWineRegistrationDTO> craftWineRegistrationDTOS = new ArrayList<>();
        Optional<Region> khersoneRegion = regionRepository.findByName("Khersone");

        for (int i = 0; i < 300; i++) {

            craftWineRegistrationDTOS.add(
                    new CraftWineRegistrationDTO(
                            "name: " + i, new BigDecimal("123"), 0f,
                            "description", (short) 20, "0.7", "12",
                            false, false, false, "winemaking",
                            "grapeVarieties", "testing notes", "store and serve advice",
                            "food pairing", "reviews and awards", "Red",
                            "Sweet",
                            khersoneRegion.orElseThrow(), null, ""
                    )
            );


        }

        List<CraftWine> collect = craftWineRegistrationDTOS.stream().map(craftWineMapper::toEntityCraftWine).collect(Collectors.toList());

        craftWineService.saveAllAndFlush(collect);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/pagination")
    public List<DataDTO<CraftWineDTOResponse>> getPagination(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {


        Page<CraftWine> page = craftWineService.findAllBy(PageRequest.of(offset, limit));

        List<DataDTO<CraftWineDTOResponse>> collected = page.stream().map(element ->
                new DataDTO<CraftWineDTOResponse>(
                        Collections.singletonList(craftWineMapper.toDTOResponse(element)),
                        page.getTotalPages(),
                        page.getTotalElements())

        ).collect(Collectors.toList());

        return new ResponseEntity<>(collected, HttpStatus.OK).getBody();
    }


}
