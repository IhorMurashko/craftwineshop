package com.craftWine.shop.service;

import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.repositories.CraftWineRepository;
import com.craftWine.shop.repositories.WineStarsRepository;
import com.craftWine.shop.utils.ImagineHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class CraftWineServiceImpl implements CraftWineService {

    private final CraftWineRepository craftWineRepository;
    private final WineStarsRepository wineStarsRepository;
    private final CraftWineMapper craftWineMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public CraftWine save(CraftWineDTO craftWineDTO, String imagePath) {


//        WineColor wineColor = Arrays.stream(WineColor.values())
//                .filter(wine -> wine.getName().equals(craftWineDTO.getWineColor()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Could not find color of wine"));
//
//        SugarConsistency sugarConsistency = Arrays.stream(SugarConsistency.values())
//                .filter(color -> color.getName().equals(craftWineDTO.getSugarConsistency()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Could not find sugar consistency of wine"));
//
//
//        CraftWine craftWine = new CraftWine(
//                craftWineDTO.getWineName(), craftWineDTO.getPrice(), craftWineDTO.getWineDescription(),
//                craftWineDTO.getQuantity(), craftWineDTO.getBottleCapacity(), craftWineDTO.getAlcohol(),
//                craftWineDTO.isNewCollection(), craftWineDTO.isBestSeller(), craftWineDTO.isSale(),
//                craftWineDTO.getWinemaking(), craftWineDTO.getGrapeVarieties(), craftWineDTO.getTastingNotes(), craftWineDTO.getStoreAndServeAdvices(),
//                craftWineDTO.getFoodPairing(), craftWineDTO.getReviewsAndAwards(),
//
//                wineColor, sugarConsistency,
//
//                craftWineDTO.getCountry(), craftWineDTO.getRegion(),
//                imagePath);

        CraftWine craftWine = craftWineMapper.toEntityCraftWine(craftWineDTO);
        craftWine.setImageUrl(imagePath);

        craftWineRepository.save(craftWine);
        return craftWine;

    }


    @Override
    public boolean save(CraftWine craftWine) {

        craftWineRepository.save(craftWine);

        return true;
    }

    @Override
    public List<CraftWine> findAll() {
        return craftWineRepository.findAll();
    }

    @Override
    public CraftWine findById(long id) {

        Optional<CraftWine> optionalCraftWine = craftWineRepository.findById(id);


        return optionalCraftWine.orElseThrow(
                () -> new IllegalArgumentException("Could not find wine with id: " + id));
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteCraftWineById(long id) throws IOException {

        Optional<CraftWine> optionalCraftWine = craftWineRepository.findById(id);


        if (optionalCraftWine.isPresent()) {
            CraftWine craftWine = optionalCraftWine.get();

            ImagineHandler.deleteWineImageFromServer(craftWine.getImageUrl());

            craftWineRepository.delete(craftWine);

            return true;
        } else {
            return false;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public short setAverageRateForTheCraftWine(long id, short rate) {
//TODO
        return 3;

    }

    @Override
    public Long getLastCraftWineId() {

        Optional<Long> longOptionalCraftWineId = craftWineRepository.getLastId();

        return longOptionalCraftWineId.orElse(1L);
    }

}
