package com.craftWine.shop.service;

import com.craftWine.shop.models.CraftWine;

import java.util.List;
import java.util.Optional;

public interface CraftWineService {

    void save(CraftWine craftWine);

    List<CraftWine> findAllByAddedTimeDesc();

    List<CraftWine> findAllByBottlesSoldCounter();

    List<CraftWine> findAll();

    Optional<CraftWine> findById(long id);

    Optional<CraftWine> findByArticle(String article);


    void deleteCraftWineById(long id);

}
