package com.craftWine.shop.models.abstracts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SequenceGenerator(name = "bucket_sequence_generator",
        sequenceName = "bucket_sequence_generator",
        allocationSize = 1)
public abstract class AbstractBucketClass {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bucket_sequence_generator")
    private long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBucketClass that)) return false;
        return Objects.equals(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}