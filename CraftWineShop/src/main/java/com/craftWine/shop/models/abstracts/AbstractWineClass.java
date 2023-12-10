package com.craftWine.shop.models.abstracts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SequenceGenerator(name = "wine_sequence_generator",
        sequenceName = "wine_sequence_generator",
        allocationSize = 1)
public abstract class AbstractWineClass {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wine_sequence_generator")
    private long id;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractWineClass that)) return false;
        return Objects.equals(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
