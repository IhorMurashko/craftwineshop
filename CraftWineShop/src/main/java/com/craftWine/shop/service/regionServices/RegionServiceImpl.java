package com.craftWine.shop.service.regionServices;

import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;


    @Override
    public void save(Region region) {
        regionRepository.save(region);
    }

    @Override
    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    @Override
    public void deleteById(long id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
        } else {
            throw new NotFoundException("Could not find region with id: " + id);
        }

    }


}
