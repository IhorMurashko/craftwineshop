package com.craftWine.shop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "produced_country")
@SequenceGenerator(name = "produced_country_sequence_generator",
        sequenceName = "produced_country_sequence_generator",
        allocationSize = 1)
public class ProducedCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produced_country_sequence_generator")
    private long id;

    private String name;

    @OneToMany(mappedBy = "producedCountry")
    private List<Region> regions;

}