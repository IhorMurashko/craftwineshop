package com.craftWine.shop.repositories;

import com.craftWine.shop.models.CraftWine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CraftWineRepository extends JpaRepository<CraftWine, Long> {



}
