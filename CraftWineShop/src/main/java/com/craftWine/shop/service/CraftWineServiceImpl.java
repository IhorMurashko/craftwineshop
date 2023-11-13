package com.craftWine.shop.service;

import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.repositories.CraftWineRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
}
