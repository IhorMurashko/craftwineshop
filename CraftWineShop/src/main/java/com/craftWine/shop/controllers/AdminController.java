package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.UpdateWineRateDTO;
import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryResponseWithSetRegionsDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.ProducedCountryMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.service.CraftWineService;
import com.craftWine.shop.service.ProducedCountryService;
import com.craftWine.shop.service.WineStarService;
import com.craftWine.shop.utils.ImagineHandler;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
    private final CraftWineMapper craftWineMapper;
    private final ProducedCountryMapper producedCountryMapper;

    @Hidden
    @GetMapping(value = "/attributes_for_add_new_wine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> attributes_for_add_new_wine() {

        List<String> sugarConsistencyList = Arrays.stream(SugarConsistency.values())
                .map(SugarConsistency::getName).collect(Collectors.toList());

        List<String> wineColorList = Arrays.stream(WineColor.values())
                .map(WineColor::getName).collect(Collectors.toList());

        List<ProducedCountry> producedCountryList = producedCountryService.findAll();


        List<ProducedCountryResponseWithSetRegionsDTO> producedCountryDTOList =
                producedCountryList.stream()
                        .map(producedCountryMapper::toProducedCountryResponseDTO)

                        .collect(Collectors.toList());


        List<List<?>> responseList = new ArrayList<>(3);
        responseList.add(sugarConsistencyList);
        responseList.add(wineColorList);
        responseList.add(producedCountryDTOList);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @Hidden
    @PostMapping(value = "/save_a_new_wine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CraftWineDTOResponse> saveNewWine(@Valid CraftWineDTO craftWineDTO,
                                                            MultipartFile wineImage) throws IOException {
        String imagePath;


        if (!wineImage.isEmpty()) {
            Long lastCraftWineId = craftWineService.getLastCraftWineId() + 1;

            imagePath = ImagineHandler.saveImageIntoServerAndReturnPath(wineImage,
                    String.valueOf(lastCraftWineId));
        } else {
            throw new IllegalArgumentException("Wine image can't be empty");
        }

        CraftWine craftWine = craftWineService.save(craftWineDTO, imagePath);

        CraftWineDTOResponse dtoResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    }


    @Hidden
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CraftWineDTOResponse> getWineById(@PathVariable("id") Long id) {
        CraftWine craftWine = craftWineService.findById(id);

        CraftWineDTOResponse craftWineDTOResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<CraftWineDTOResponse>(craftWineDTOResponse, HttpStatus.OK);
    }

    @Hidden
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWineFromShop(@PathVariable("id") Long id) throws IOException {

        craftWineService.deleteCraftWineById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Hidden
    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CraftWineDTOResponse> updateWine(@PathVariable("id") Long id,
                                                           @Valid CraftWineDTO craftWineDTO,
                                                           @Nullable MultipartFile wineImage) throws IOException {

        CraftWine craftWine = craftWineService.findById(id);

        String imagePath = craftWine.getImageUrl();

        if (wineImage != null) {
            imagePath = ImagineHandler.saveImageIntoServerAndReturnPath(wineImage, String.valueOf(id));
        }

        craftWine = craftWineMapper.toEntityCraftWine(craftWineDTO);
        craftWine.setImageUrl(imagePath);

        craftWineService.save(craftWine);


        CraftWineDTOResponse craftWineDTOResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<CraftWineDTOResponse>(craftWineDTOResponse, HttpStatus.OK);
    }


    @Hidden
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
