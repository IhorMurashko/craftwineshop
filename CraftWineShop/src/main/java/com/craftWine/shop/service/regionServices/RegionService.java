package com.craftWine.shop.service.regionServices;

import com.craftWine.shop.models.Region;

import java.util.Optional;

public interface RegionService {
    void save (Region region);

    Optional<Region> findByName (String name);
}
