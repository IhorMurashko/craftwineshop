package com.craftWine.shop.service;

import com.craftWine.shop.models.ProducedCountry;

import java.util.List;

public interface ProducedCountryService {

   void save (ProducedCountry producedCountry);

   List<ProducedCountry> findAll();
}
