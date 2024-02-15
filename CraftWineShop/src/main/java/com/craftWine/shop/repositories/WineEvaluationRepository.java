package com.craftWine.shop.repositories;

import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WineEvaluationRepository extends JpaRepository<WineEvaluation, Long> {

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Query("SELECT  FUNCTION('ROUND', AVG(ws.evaluation)) FROM WineEvaluation ws WHERE ws.craftWine=:craftWine")
    Short getAverageRateForTheWine(@Param("craftWine") CraftWine craftWine);

    @Query("SELECT ws.id FROM WineEvaluation ws WHERE ws.user=:user AND ws.craftWine=:craftWine ")
    Optional<Long> isExistStarForTheWineByUser(@Param("user") User user,
                                               @Param("craftWine") CraftWine craftWine);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query("UPDATE WineEvaluation ws  SET ws.evaluation=:evaluation  WHERE ws.user=:user AND ws.craftWine=:craftWine ")
    void updateWineStar(@Param("user") User user, @Param("craftWine") CraftWine craftWine,
                        @Param("evaluation") short evaluation);


}
