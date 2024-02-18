package com.craftWine.shop.service.crafWineServices;

import com.craftWine.shop.dto.wineDTO.UpdateCraftWineDTO;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CraftWineService {

    CraftWine save(UpdateCraftWineDTO updateCraftWineDTO, MultipartFile wineImage) throws IOException;

    boolean save(CraftWine craftWine);


    List<CraftWine> findAll();

    CraftWine findById(long id);


    boolean deleteCraftWineById(long id) throws IOException;


    Long getLastCraftWineId();

    List<CraftWine> getByProducedCountry(ProducedCountry producedCountry);

    void saveAllAndFlush(List<CraftWine> craftWines);


    List<CraftWine> findCraftWineByWineTimePromotion(boolean promotion);

}
