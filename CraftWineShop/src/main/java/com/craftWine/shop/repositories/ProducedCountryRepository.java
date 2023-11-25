package com.craftWine.shop.repositories;

import com.craftWine.shop.models.ProducedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducedCountryRepository extends JpaRepository<ProducedCountry, Long> {

    List<ProducedCountry> findAll();


}
