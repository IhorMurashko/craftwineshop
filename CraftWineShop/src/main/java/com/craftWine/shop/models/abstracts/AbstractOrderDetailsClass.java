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
@SequenceGenerator(name = "order_details_sequence_generator",
        sequenceName = "order_details_sequence_generator",
        allocationSize = 1)
public abstract class AbstractOrderDetailsClass {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_sequence_generator")
    private long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractOrderDetailsClass that)) return false;
        return Objects.equals(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
