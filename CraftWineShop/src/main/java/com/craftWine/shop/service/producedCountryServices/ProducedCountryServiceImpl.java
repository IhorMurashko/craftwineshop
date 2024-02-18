package com.craftWine.shop.service.producedCountryServices;

import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.repositories.ProducedCountryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class ProducedCountryServiceImpl implements ProducedCountryService {

    private final ProducedCountryRepository producedCountryRepository;

    @Override
    public void save(ProducedCountry producedCountry) {
        producedCountryRepository.save(producedCountry);
    }

    @Override
    public void saveAllAndFlush(List<ProducedCountry> producedCountryList) {

        if (producedCountryList.isEmpty()) {
            throw new IllegalArgumentException("List of produced countries is empty");
        }

        producedCountryRepository.saveAllAndFlush(producedCountryList);
    }

    @Override
    public List<ProducedCountry> findAll() {
        return producedCountryRepository.findAll();
    }

    @Override
    public Optional<ProducedCountry> findByName(String name) {
        return producedCountryRepository.findByName(name);
    }

    @Override
    public Optional<ProducedCountry> findById(long id) {
        return producedCountryRepository.findById(id);
    }

    @Override
    public List<ProducedCountry> findByPromotionTime(boolean promotionTime) {
        return producedCountryRepository.findByPromotionTime(promotionTime);
    }

    @Override
    public void updateFieldTimePromotion(ProducedCountry producedCountry, boolean isPromotionTime) {
        if (producedCountry == null) {
            throw new IllegalArgumentException("produced country cannot be null");
        }
        producedCountryRepository.updateFieldTimePromotion(producedCountry, isPromotionTime);
    }

    @Override
    public void deleteCountryById(long id) {

        if (producedCountryRepository.existsById(id)) {
            producedCountryRepository.deleteById(id);
        } else {
            throw new NotFoundException("Could not find country with id: " + id);
        }
    }
}
