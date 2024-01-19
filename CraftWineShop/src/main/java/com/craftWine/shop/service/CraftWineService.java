package com.craftWine.shop.service;

import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.models.CraftWine;

import java.io.IOException;
import java.util.List;

public interface CraftWineService {

    CraftWine save(CraftWineDTO craftWineDTO, String imagePath);

    boolean save(CraftWine craftWine);


    List<CraftWine> findAll();

    CraftWine findById(long id);


    boolean deleteCraftWineById(long id) throws IOException;

    short setAverageRateForTheCraftWine(long id, short rate);

    Long getLastCraftWineId();

}
