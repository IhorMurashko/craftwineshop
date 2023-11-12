package com.craftWine.shop.models;

import com.craftWine.shop.models.abstracts.AbstractBucketClass;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users_buckets")
public class Bucket extends AbstractBucketClass {
    public Bucket() {
        super();
    }

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "users_buckets",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "wine_id")
    )
    private List<CraftWine> bucketList;

    private short quantity;


}
