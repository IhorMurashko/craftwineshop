package com.craftWine.shop.service;

import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.repositories.CraftWineRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class CraftWineServiceImpl implements CraftWineService {

    private final CraftWineRepository craftWineRepository;

    @Override
    public void save(CraftWine craftWine) {
        craftWineRepository.save(craftWine);
    }

    @Override
    public List<CraftWine> findAllByAddedTimeDesc() {
        return new ArrayList<>(craftWineRepository.findAllByAddedDateTimeDesc());
    }

    @Override
    public List<CraftWine> findAllByBottlesSoldCounter() {
        return new ArrayList<>(craftWineRepository.findAllByBottlesSoldCounter());
    }

    @Override
    public List<CraftWine> findAll() {
        return craftWineRepository.findAll();
    }

    @Override
    public Optional<CraftWine> findById(long id) {
        return craftWineRepository.findById(id);
    }

    @Override
    public Optional<CraftWine> findByArticle(String article) {
        return craftWineRepository.findCraftWineByWineArticle(article);
    }

    @Override
    public void deleteCraftWineById(long id) {
        craftWineRepository.deleteById(id);
    }

}
