package com.craftWine.shop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regions")
@SequenceGenerator(name = "country_region_sequence_generator",
        sequenceName = "country_region_sequence_generator",
        allocationSize = 1)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_region_sequence_generator")
    private long id;
    private String name;


    @ManyToOne
    @JoinColumn(name = "produced_country_id")
    private ProducedCountry producedCountry;

}