package com.craftWine.shop.repositories;

import com.craftWine.shop.models.ProducedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducedCountryRepository extends JpaRepository<ProducedCountry, Long> {

    @Transactional(readOnly = true)
    List<ProducedCountry> findAll();


    @Transactional
    @Override
    <S extends ProducedCountry> List<S> saveAllAndFlush(Iterable<S> entities);

    @Transactional(readOnly = true)
    @Query("SELECT pc FROM ProducedCountry pc where pc.name=:name")
    Optional<ProducedCountry> findByName(@Param("name") String name);


    @Transactional
    @Modifying
    @Query("UPDATE ProducedCountry pc SET pc.isPromotionTime=:isPromotionTime WHERE pc=:producedCountry")
    void updateFieldTimePromotion(@Param("producedCountry") ProducedCountry producedCountry,
                                  @Param("isPromotionTime") boolean isPromotionTime);


    @Transactional
    @Query("SELECT pc FROM ProducedCountry pc WHERE pc.isPromotionTime=:promotionTime ")
    List<ProducedCountry> findByPromotionTime(@Param("promotionTime") boolean promotionTime);
}
