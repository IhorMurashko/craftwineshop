package com.craftWine.shop.repositories;

import com.craftWine.shop.models.ProducedCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducedCountryRepository extends JpaRepository<ProducedCountry, Long> {
}
