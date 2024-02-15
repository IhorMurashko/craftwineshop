package com.craftWine.shop.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "wine_stars"
//        , uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "craft_wine_id"})}
)
@SequenceGenerator(name = "wine_stars_sequence_generator",
        sequenceName = "wine_stars_sequence_generator",
        allocationSize = 1)
public class WineEvaluation {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_stars_sequence_generator")
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private CraftWine craftWine;

    private short evaluation;


    public WineEvaluation() {
    }

    public WineEvaluation(User user, CraftWine craftWine, short evaluation) {
        this();
        this.user = user;
        this.craftWine = craftWine;
        this.evaluation = evaluation;
    }

    public WineEvaluation(long id, User user, CraftWine craftWine, short evaluation) {
        this.id = id;
        this.user = user;
        this.craftWine = craftWine;
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WineEvaluation wineEvaluation)) return false;
        return Objects.equals(getUser(), wineEvaluation.getUser()) && Objects.equals(getCraftWine(), wineEvaluation.getCraftWine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getCraftWine());
    }
}
