package com.craftWine.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "regions")
@SequenceGenerator(name = "country_region_sequence_generator",
        sequenceName = "country_region_sequence_generator",
        allocationSize = 1)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_region_sequence_generator")
    private long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produced_country_id")
    private ProducedCountry producedCountry;

    public Region(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Region region)) return false;
        return getId() == region.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}