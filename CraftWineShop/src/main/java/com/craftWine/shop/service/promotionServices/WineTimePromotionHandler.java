package com.craftWine.shop.service.promotionServices;

import com.craftWine.shop.models.ProducedCountry;

import java.util.List;

public interface WineTimePromotionHandler {

    void refreshCountry();

    List<?> getPromotions();


    void resetDiscountPromotionForTheCurrentTime(List<ProducedCountry> craftWineList);


    void applyWineTimeDiscount(List<ProducedCountry> producedCountryList);


}
