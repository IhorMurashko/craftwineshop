package com.craftWine.shop.repositories;

import com.craftWine.shop.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Transactional(readOnly = true)
    @Query("select rg from Region  rg where rg.name=:name")
    Optional<Region> findByName(@Param("name") String name);
}
