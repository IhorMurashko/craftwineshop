package com.craftWine.shop.service.crafWineServices;

import com.craftWine.shop.dto.wineDTO.UpdateCraftWineDTO;
import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.repositories.CraftWineRepository;
import com.craftWine.shop.service.imagineHandlerService.ImageHandlerService;
import com.craftWine.shop.utils.PercentageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CraftWineServiceImpl implements CraftWineService {

    private final CraftWineRepository craftWineRepository;
    private final CraftWineMapper craftWineMapper;
    private final ImageHandlerService imageHandlerService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public CraftWine save(UpdateCraftWineDTO updateCraftWineDTO, MultipartFile wineImage) throws IOException {

        CraftWine craftWine;
        Long wineId = updateCraftWineDTO.id();
        String imagePath;


        if (wineId != null && craftWineRepository.existsById(wineId)) {
            craftWine = craftWineRepository.findById(updateCraftWineDTO.id()).orElseThrow(() ->
                    new NotFoundException("Could not find wine with id: " + updateCraftWineDTO.id()));

            imagePath =
                    wineImage == null
                            ? craftWine.getImageUrl()
                            : imageHandlerService.saveImageIntoServerAndReturnPath(wineImage, String.valueOf(wineId));


        } else {
            wineId = getLastCraftWineId() + 1;
            imagePath = imageHandlerService.saveImageIntoServerAndReturnPath(wineImage, String.valueOf(wineId));
        }


//        if (wineImage != null && !wineImage.isEmpty()) {
//            imagePath = imageHandlerService.saveImageIntoServerAndReturnPath(wineImage, String.valueOf(wineId));
//        }


        craftWine = craftWineMapper.toEntityCraftWine(updateCraftWineDTO);


        BigDecimal price = updateCraftWineDTO.originalPrice();

        if (craftWine.getAdminDiscountPercentage() > 0) {
            price = PercentageHandler.getPercentageFromPrice(updateCraftWineDTO.originalPrice(),
                    updateCraftWineDTO.adminDiscountPercentage());
            craftWine.setSale(true);
        }


        craftWine.setImageUrl(imagePath);
        craftWine.setPrice(price);

        return craftWineRepository.save(craftWine);
    }


    @Override
    public boolean save(CraftWine craftWine) {
        craftWineRepository.saveAndFlush(craftWine);
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
                () -> new NotFoundException("Could not find wine with id: " + id));
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public boolean deleteCraftWineById(long id) throws IOException {

        Optional<CraftWine> optionalCraftWine = craftWineRepository.findById(id);


        if (optionalCraftWine.isPresent()) {
            CraftWine craftWine = optionalCraftWine.get();

            boolean deleteWineImage = imageHandlerService.deleteWineImage(String.valueOf(craftWine.getId()));

            if (deleteWineImage) {
                craftWineRepository.delete(craftWine);

                return true;
            } else {
                throw new IllegalStateException("Could not find image");
            }


        } else {
            throw new NotFoundException("Could not find wine with id: " + id);
        }
    }


    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Long getLastCraftWineId() {

        Optional<Long> longOptionalCraftWineId = craftWineRepository.getLastId();

        return longOptionalCraftWineId.orElse(0L);
    }

    @Override
    public List<CraftWine> getByProducedCountry(ProducedCountry producedCountry) {
        if (producedCountry == null) {
            throw new IllegalArgumentException("produced country cannot be null");
        }
        return craftWineRepository.getCraftWinesByCountry(producedCountry);
    }

    @Override
    public void saveAllAndFlush(List<CraftWine> craftWines) {
        craftWineRepository.saveAllAndFlush(craftWines);
    }

    @Override
    public List<CraftWine> findCraftWineByWineTimePromotion(boolean promotion) {
        return craftWineRepository.findCraftWineByWineTimePromotion(promotion);
    }

}
