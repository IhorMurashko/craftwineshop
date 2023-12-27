package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.service.CraftWineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Test {

    private final CraftWineService craftWineService;
    private final CraftWineMapper craftWineMapper;

    @GetMapping("/test")
    public ResponseEntity<List<CraftWineDTOResponse>> test() {


//
        List<CraftWine> all = craftWineService.findAll();

        List<CraftWineDTOResponse> responses =
                all.stream().map(craftWineMapper::toDTOResponse).toList();

//        CraftWine craftWine = all.get(0);
//
//        TestDTO dto = wineMapper.toDTO(craftWine);
//
//        return ResponseEntity.ok(dto);
        return ResponseEntity.ok(responses);
    }

}
