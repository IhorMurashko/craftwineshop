package com.craftWine.shop.service.regionServices;

import com.craftWine.shop.models.Region;
import com.craftWine.shop.repositories.RegionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
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
     return  regionRepository.findByName(name);
    }
}
