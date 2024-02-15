package com.craftWine.shop.service.promotionServices;

import com.craftWine.shop.models.ProducedCountry;

import java.io.IOException;

public interface CheckInformationAboutTheCountry {

    ProducedCountry findTheCountryAndCoordinatesOfTheCapitalByItsName(String country) throws IOException;

}
