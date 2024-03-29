package com.craftWine.shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wine_comments")
@SequenceGenerator(name = "wine_comments_sequence_generator",
        sequenceName = "wine_comments_sequence_generator",
        allocationSize = 1)
public class WineComment {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_comments_sequence_generator")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private CraftWine craftWine;

    private String comment;
    private LocalDateTime addedCommentTime;

}