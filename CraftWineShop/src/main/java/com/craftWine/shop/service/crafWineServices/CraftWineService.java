package com.craftWine.shop.service.crafWineServices;

import com.craftWine.shop.dto.wineDTO.CraftWineRegistrationDTO;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CraftWineService {

    CraftWine save(CraftWineRegistrationDTO craftWineRegistrationDTO, String imagePath);

    boolean save(CraftWine craftWine);


    List<CraftWine> findAll();

    Optional<CraftWine> findById(long id);


    boolean deleteCraftWineById(long id) throws IOException;


    Long getLastCraftWineId();

    List<CraftWine> getByProducedCountry(ProducedCountry producedCountry);

    void saveAllAndFlush(List<CraftWine> craftWines);


    List<CraftWine> findCraftWineByWineTimePromotion(boolean promotion);

    Page<CraftWine> findAllBy(Pageable pageable);

}
