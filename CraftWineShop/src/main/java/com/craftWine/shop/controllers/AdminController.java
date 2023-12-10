package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.ProducedCountryDTO;
import com.craftWine.shop.dto.UpdateWineRateDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineForUserResponseDTO;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.service.CraftWineService;
import com.craftWine.shop.service.ProducedCountryService;
import com.craftWine.shop.service.WineStarService;
import com.craftWine.shop.utils.ImagineHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {


    private final CraftWineService craftWineService;
    private final ProducedCountryService producedCountryService;
    private final WineStarService wineStarService;

    @PostMapping(value = "/save_a_new_wine")
    public ResponseEntity<?> saveNewWine(
            @Valid CraftWineDTO craftWineDTO,
            @NotNull MultipartFile wineImage

    ) throws IOException {
        String imagePath;


        if (!wineImage.isEmpty()) {
            imagePath = ImagineHandler.saveImageIntoServerAndReturnPath(wineImage, craftWineDTO.getWineArticle());
        } else {
            throw new IllegalArgumentException("Wine image cant be empty");
        }


        if (craftWineService.save(craftWineDTO, imagePath)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping(value = "/add_new_wine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewWine() {

        List<String> sugarConsistencyList = Arrays.stream(SugarConsistency.values())
                .map(SugarConsistency::getName).collect(Collectors.toList());

        List<String> wineColorList = Arrays.stream(WineColor.values())
                .map(WineColor::getName).collect(Collectors.toList());

        List<ProducedCountry> producedCountryList = producedCountryService.findAll();


        List<ProducedCountryDTO> producedCountryDTOList =
                producedCountryList.stream()
                        .map(producedCountry -> new ProducedCountryDTO(producedCountry.getId(), producedCountry.getName(), producedCountry.getRegions())
                        )
                        .collect(Collectors.toList());


        List<List<?>> responseList = new ArrayList<>(3);
        responseList.add(sugarConsistencyList);
        responseList.add(wineColorList);
        responseList.add(producedCountryDTOList);

        return ResponseEntity.ok(responseList);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CraftWineForUserResponseDTO> getCraftWineDTOById(@PathVariable("id") long id) {

        CraftWine craftWine = craftWineService.findById(id);

        CraftWineForUserResponseDTO craftWineDTO = new CraftWineForUserResponseDTO(
                craftWine.getId(),
                craftWine.getWineArticle(), craftWine.getWineName(), craftWine.getPrice(),
                craftWine.getDiscount(), craftWine.getPriceWithDiscount(),
                craftWine.getWineDescription(), craftWine.getQuantity(), craftWine.getBottleCapacity(),
                craftWine.getAlcohol(), craftWine.isNewCollection(), craftWine.isBestSeller(), craftWine.isSale(),
                craftWine.getWinemaking(), craftWine.getGrapeVarieties(), craftWine.getTastingNotes(), craftWine.getStoreAndServeAdvices(),
                craftWine.getFoodPairing(), craftWine.getReviewsAndAwards(), craftWine.getWineColor().getName(),
                craftWine.getSugarConsistency().getName(), craftWine.getCountry(), craftWine.getRegion(),
                craftWine.getRate(), craftWine.getWineComments(), craftWine.getBottlesSoldCounter(),
                craftWine.getAddedDateTime(), craftWine.getImageUrl());


        return ResponseEntity.ok(craftWineDTO);


    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWineFromShop(@PathVariable("id") Long id) throws IOException {

        if (craftWineService.deleteCraftWineById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping(value = "/updateRate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateWineRate(@RequestBody UpdateWineRateDTO updateWineRateDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (wineStarService.addRateForTheWine(email, updateWineRateDTO.getWineId(), updateWineRateDTO.getRate())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
