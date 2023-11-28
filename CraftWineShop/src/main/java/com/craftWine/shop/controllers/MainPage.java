package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.CraftWineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class MainPage {

    @Autowired
    private final CraftWineService craftWineService;

    @GetMapping(value = "/craft_wines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<List<CraftWineDTOResponse>>> listResponseEntity() {


        List<CraftWine> craftWineList = craftWineService.findAll();


        List<CraftWineDTOResponse> sortedWineByAddedTime =
                craftWineList.stream()
                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
                        .sorted(Comparator.comparing(CraftWineDTOResponse::getAddedDateTime).reversed())
                        .collect(Collectors.toList());


        List<CraftWineDTOResponse> sortedWineBySoldCounter =
                craftWineList.stream()
                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
                        .sorted(Comparator.comparing(CraftWineDTOResponse::getBottlesSoldCounter).reversed()
                                .thenComparing(CraftWineDTOResponse::getWineName))
                        .collect(Collectors.toList());


        //TODO: "sales" admin will make
        List<CraftWineDTOResponse> sortedWineByIsSales =
                craftWineList.stream()
                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
                        .filter(CraftWineDTOResponse::isBestSeller)
                        .sorted(Comparator.comparing(CraftWineDTOResponse::getBottlesSoldCounter).reversed())
                                .collect(Collectors.toList());


        return ResponseEntity.ok(new ArrayList<>() {{
            add(sortedWineByAddedTime);
            add(sortedWineBySoldCounter);
            add(sortedWineByIsSales);
        }});

    }
}