package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryResponseWithSetRegionsDTO;
import com.craftWine.shop.dto.regionDTO.NewRegionDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.dto.wineDTO.UpdateCraftWineDTO;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.ProducedCountryMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.imagineHandlerService.ImageHandlerService;
import com.craftWine.shop.service.producedCountryServices.ProducedCountryService;
import com.craftWine.shop.service.promotionServices.CheckInformationAboutTheCountry;
import com.craftWine.shop.service.regionServices.RegionService;
import com.craftWine.shop.utils.SwitchCaseToCapitalize;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Hidden
@Tag(name = "адмін контроллер",
        description = "навігація для адміністратора")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
@Validated
public class AdminController {


    private final CraftWineService craftWineService;
    private final ProducedCountryService producedCountryService;
    private final CraftWineMapper craftWineMapper;
    private final ProducedCountryMapper producedCountryMapper;
    private final CheckInformationAboutTheCountry checkInformationAboutTheCountry;
    private final RegionService regionService;
    private final ImageHandlerService imageHandlerService;

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

    @PostMapping(value = "/save_wine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CraftWineDTOResponse> saveNewWine(@Valid UpdateCraftWineDTO updateCraftWineDTO,
                                                            @Nullable MultipartFile wineImage) throws IOException {


        CraftWine craftWine = craftWineService.save(updateCraftWineDTO, wineImage);

        CraftWineDTOResponse dtoResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    }


    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CraftWineDTOResponse> getWineById(@PathVariable("id") Long id) {
        CraftWine craftWine = craftWineService.findById(id);

        CraftWineDTOResponse craftWineDTOResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<CraftWineDTOResponse>(craftWineDTOResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteWineFromShop(@PathVariable("id") Long id) throws IOException {

        craftWineService.deleteCraftWineById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CraftWineDTOResponse> updateWine(@PathVariable("id") Long id,
                                                           @Valid UpdateCraftWineDTO updateCraftWineDTO,
                                                           @Nullable MultipartFile wineImage) throws IOException {

        CraftWine craftWine = craftWineService.findById(id);

        String imagePath = craftWine.getImageUrl();

        if (wineImage != null) {
            imagePath = imageHandlerService.saveImageIntoServerAndReturnPath(wineImage, String.valueOf(updateCraftWineDTO.id()));
        }

        craftWine = craftWineMapper.toEntityCraftWine(updateCraftWineDTO);
        craftWine.setImageUrl(imagePath);

        craftWineService.save(craftWine);


        CraftWineDTOResponse craftWineDTOResponse = craftWineMapper.toDTOResponse(craftWine);

        return new ResponseEntity<CraftWineDTOResponse>(craftWineDTOResponse, HttpStatus.OK);
    }

    @GetMapping("/add_new_country/{countryName}")
    public ResponseEntity<List<ProducedCountryResponseWithSetRegionsDTO>> addNewCountry(@PathVariable("countryName") String countryName) throws IOException {

        ProducedCountry producedCountry = checkInformationAboutTheCountry.findTheCountryAndCoordinatesOfTheCapitalByItsName(countryName);

        if (producedCountry == null) {
            throw new IllegalArgumentException("Name of country " + countryName + " isn't correct");
        } else {

            List<ProducedCountry> producedCountryList = producedCountryService.findAll();

            List<ProducedCountryResponseWithSetRegionsDTO> producedCountryDTOList =
                    producedCountryList.stream()
                            .map(producedCountryMapper::toProducedCountryResponseDTO)

                            .collect(Collectors.toList());

            return new ResponseEntity<>(producedCountryDTOList, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/delete_country/{id}")
    public ResponseEntity<HttpStatus> deleteCountry(@PathVariable("id") long id) {

        producedCountryService.deleteCountryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/add_new_region")
    public ResponseEntity<List<ProducedCountryResponseWithSetRegionsDTO>> addNewRegion(@RequestBody NewRegionDTO regionDTO) {

        Optional<ProducedCountry> optionalProducedCountry
                = producedCountryService.findById(regionDTO.producedCountryId());

        if (optionalProducedCountry.isEmpty()) {
            throw new IllegalArgumentException("wrong country id");
        }

        if (regionService.findByName(regionDTO.name()).isPresent()) {
            throw new IllegalArgumentException("region with name " + regionDTO.name() + " has already exist");
        }

        ProducedCountry producedCountry = optionalProducedCountry.get();

        Region region = new Region(SwitchCaseToCapitalize.switchCaseToCapitalize(regionDTO.name()));
        region.setProducedCountry(producedCountry);

        regionService.save(region);

        List<ProducedCountry> producedCountryList = producedCountryService.findAll();

        List<ProducedCountryResponseWithSetRegionsDTO> producedCountryDTOList =
                producedCountryList.stream()
                        .map(producedCountryMapper::toProducedCountryResponseDTO)

                        .collect(Collectors.toList());

        return new ResponseEntity<>(producedCountryDTOList, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete_region/{id}")
    public ResponseEntity<HttpStatus> deleteRegion(@PathVariable("id") long id) {

        regionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}