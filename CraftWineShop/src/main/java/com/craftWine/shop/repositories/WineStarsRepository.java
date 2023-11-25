package com.craftWine.shop.repositories;

import com.craftWine.shop.models.WineStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WineStarsRepository extends JpaRepository<WineStar, Long> {

    @Query("SELECT AVG(ws.star) FROM WineStar ws WHERE ws.craftWine = :wineId")
    short getAverageRateForThisWine(@Param("wineId") Long wineId);
}
