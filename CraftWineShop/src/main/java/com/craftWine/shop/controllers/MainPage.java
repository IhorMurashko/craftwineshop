package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.CraftWineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Головна сторінка", description = "представлення вина")
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/main")
//@CrossOrigin(value = "http://localhost:5500")
public class MainPage {

    @Autowired
    private final CraftWineService craftWineService;
    @Autowired
    private final CraftWineMapper craftWineMapper;


    @Operation(
            summary = "контроллер повертає колекцію вин",
            description = "кожен обєкт репрезунтує вино, яке є на даний момент в магазині",

            responses = {@ApiResponse(
                    responseCode = "200", description = "статус ОК (200) та колекцію (массив) обєектів, де кожен репрезунтує вино"
            )}

    )
    @GetMapping(value = "/craft_wines", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<List<CraftWineDTOResponse>>> listResponseEntity() {
    public ResponseEntity<List<CraftWineDTOResponse>> allCraftWines() {

        List<CraftWine> craftWineList = craftWineService.findAll();


        List<CraftWineDTOResponse> craftWineDTOResponseList =
                craftWineList
                        .stream()
                        .map(craftWineMapper::toDTOResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(craftWineDTOResponseList);
    }

//        List<CraftWineDTOResponse> craftWineDTOResponseList = craftWineList.stream()
//                .map(craftWine ->
//                        new CraftWineDTOResponse(
//                                craftWine.getId(),
//                                craftWine.getWineName(),
//                                craftWine.getPrice(),
//                                craftWine.getDiscount(),
//                                craftWine.getPriceWithDiscount(),
//                                craftWine.getWineDescription(),
//                                craftWine.getQuantity(),
//                                craftWine.getBottleCapacity(),
//                                craftWine.getAlcohol(),
//                                craftWine.isNewCollection(),
//                                craftWine.isBestSeller(),
//                                craftWine.isBestSeller(),
//                                craftWine.getWinemaking(),
//                                craftWine.getGrapeVarieties(),
//                                craftWine.getTastingNotes(),
//                                craftWine.getStoreAndServeAdvices(),
//                                craftWine.getFoodPairing(),
//                                craftWine.getReviewsAndAwards(),
//                                craftWine.getWineColor().getName(),
//                                craftWine.getSugarConsistency().getName(),
//                                new ProducedCountryDTO(craftWine.getCountry().getId(), craftWine.getCountry().getName()),
////                                craftWine.getCountry(),
//                                new RegionDTO(craftWine.getRegion().getId(), craftWine.getRegion().getName()),
////                                craftWine.getRegion(),
//                                craftWine.getRate(),
//
//                                new ArrayList<WineCommentDTO>() {{
//                                    craftWine.getWineComments().stream().map(wineComment -> new WineCommentDTO(wineComment.getId(),
//                                            wineComment.getUser().getFirstName(), wineComment.getUser().getLastName(), wineComment.getComment(),
//                                            wineComment.getAddedCommentTime())).collect(Collectors.toList());
//                                }},
////                                craftWine.getWineComments(),
//                                craftWine.getBottlesSoldCounter(),
//                                craftWine.getAddedDateTime(),
//                                craftWine.getImageUrl()
//                        ))
//
//                        .
//
//                collect(Collectors.toList());


//        return ResponseEntity.ok(craftWineDTOResponseList);


//        List<CraftWineDTOResponse> sortedWineByAddedTime =
//                craftWineList.stream()
//                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
//                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
//                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
//                        .sorted(Comparator.comparing(CraftWineDTOResponse::getAddedDateTime).reversed())
//                        .collect(Collectors.toList());
//
//
//        List<CraftWineDTOResponse> sortedWineBySoldCounter =
//                craftWineList.stream()
//                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
//                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
//                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
//                        .sorted(Comparator.comparing(CraftWineDTOResponse::getBottlesSoldCounter).reversed()
//                                .thenComparing(CraftWineDTOResponse::getWineName))
//                        .collect(Collectors.toList());
//
//
//        List<CraftWineDTOResponse> sortedWineByIsSales =
//                craftWineList.stream()
//                        .map(craftWine -> new CraftWineDTOResponse(craftWine.getWineArticle(), craftWine.getWineName(),
//                                craftWine.getCountry().getName(), craftWine.getRegion().getName(),
//                                craftWine.getBottlesSoldCounter(), craftWine.getAddedDateTime(), craftWine.isBestSeller()))
//                        .filter(CraftWineDTOResponse::isBestSeller)
//                        .sorted(Comparator.comparing(CraftWineDTOResponse::getBottlesSoldCounter).reversed())
//                        .collect(Collectors.toList());
//
//
//        return ResponseEntity.ok(new ArrayList<>() {{
//            add(sortedWineByAddedTime);
//            add(sortedWineBySoldCounter);
//            add(sortedWineByIsSales);
//        }});

//    }
}
