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
public class WineStar {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_stars_sequence_generator")
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private CraftWine craftWine;

    private short star;


    public WineStar() {
    }

    public WineStar(User user, CraftWine craftWine, short star) {
        this();
        this.user = user;
        this.craftWine = craftWine;
        this.star = star;
    }

    public WineStar(long id, User user, CraftWine craftWine, short star) {
        this.id = id;
        this.user = user;
        this.craftWine = craftWine;
        this.star = star;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WineStar wineStar)) return false;
        return Objects.equals(getUser(), wineStar.getUser()) && Objects.equals(getCraftWine(), wineStar.getCraftWine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getCraftWine());
    }
}
