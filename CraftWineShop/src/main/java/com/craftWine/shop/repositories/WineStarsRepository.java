package com.craftWine.shop.repositories;

import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WineStarsRepository extends JpaRepository<WineStar, Long> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query("SELECT  AVG (ws.star) FROM WineStar ws WHERE ws.craftWine = ?1")
    Short getAverageRateForTheWine(CraftWine craftWine);

    @Query("SELECT ws.id FROM WineStar ws WHERE ws.user=?1 AND ws.craftWine=?2 ")
    Long isExistStarForTheWineByUser(User user, CraftWine craftWine);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query("UPDATE WineStar ws  SET ws.star=?3  WHERE ws.user=?1 AND ws.craftWine=?2 ")
    void updateWineStar(User user, CraftWine craft, short rate);


}
