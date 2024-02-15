package com.craftWine.shop.service.producedCountryServices;

import com.craftWine.shop.models.ProducedCountry;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProducedCountryService {

    void save(ProducedCountry producedCountry);

    void saveAllAndFlush(List<ProducedCountry> producedCountryList);

    List<ProducedCountry> findAll();

    Optional<ProducedCountry> findByName(String name);

    Optional<ProducedCountry> findById(long id);

    List<ProducedCountry> findByPromotionTime(boolean promotionTime);

    void updateFieldTimePromotion(ProducedCountry producedCountry, boolean isPromotionTime);


}
