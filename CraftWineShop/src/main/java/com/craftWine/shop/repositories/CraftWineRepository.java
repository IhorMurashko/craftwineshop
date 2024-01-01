package com.craftWine.shop.repositories;

import com.craftWine.shop.models.CraftWine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CraftWineRepository extends JpaRepository<CraftWine, Long> {
    @Query("select cw from CraftWine cw order by cw.addedDateTime DESC")
    List<CraftWine> findAllByAddedDateTimeDesc();


    @Query("select cw from CraftWine cw order by cw.bottlesSoldCounter DESC")
    List<CraftWine> findAllByBottlesSoldCounter();


    @Query("select cw.imageUrl from CraftWine cw where cw.id=:id ")
    String findImagePathById(@Param("id") Long id);

    @Query("select MAX(cw.id) from CraftWine cw")
    Optional<Long> getLastId();

}
