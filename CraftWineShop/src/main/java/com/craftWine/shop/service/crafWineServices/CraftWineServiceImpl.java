package com.craftWine.shop.service.crafWineServices;

import com.craftWine.shop.dto.wineDTO.CraftWineRegistrationDTO;
import com.craftWine.shop.exceptions.UserNotFoundException;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.repositories.CraftWineRepository;
import com.craftWine.shop.utils.ImagineHandler;
import com.craftWine.shop.utils.PercentageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CraftWineServiceImpl implements CraftWineService {

    private final CraftWineRepository craftWineRepository;
    private final CraftWineMapper craftWineMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public CraftWine save(CraftWineRegistrationDTO craftWineRegistrationDTO, String imagePath) {

        CraftWine craftWine = craftWineMapper.toEntityCraftWine(craftWineRegistrationDTO);

        BigDecimal price = craftWineRegistrationDTO.originalPrice();

        if (craftWine.getAdminDiscountPercentage() > 0) {
            price = PercentageHandler.getPercentageFromPrice(craftWineRegistrationDTO.originalPrice(),
                    craftWineRegistrationDTO.adminDiscountPercentage());
            craftWine.setSale(true);
        }


        craftWine.setImageUrl(imagePath);
        craftWine.setPrice(price);

        craftWineRepository.save(craftWine);
        return craftWine;


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
    public Optional<CraftWine> findById(long id) {

        return craftWineRepository.findById(id);
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
            throw new UserNotFoundException("Could not find wine with id: " + id);
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

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<CraftWine> findAllBy(Pageable pageable) {
        return craftWineRepository.findAllBy(pageable);
    }

}
