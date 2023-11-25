package com.craftWine.shop.models;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wine_stars")
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

}
