package com.craftWine.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;


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

    @Column(unique = true)
    private String name;

    private float capitalLAT;
    private float capitalLNG;

    private boolean isPromotionTime;


    @JsonIgnore
    @OneToMany(mappedBy = "producedCountry", fetch = FetchType.LAZY)
    private Set<Region> regions;


    public ProducedCountry(String name) {
        this.name = name;
    }

    public ProducedCountry(String name, float capitalLAT, float capitalLNG) {
        this.name = name;
        this.capitalLAT = capitalLAT;
        this.capitalLNG = capitalLNG;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ProducedCountry that)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}