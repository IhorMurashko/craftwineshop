package com.craftWine.shop.models;

import com.craftWine.shop.models.abstracts.AbstractUserCartClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@Entity
@Table(name = "user_cart")
public class UserCart extends AbstractUserCartClass {
    public UserCart() {
        super();
    }

    public UserCart(User user) {
        super();
        this.user = user;
    }

    @JsonIgnore
    @OneToOne
    private User user;


    @ElementCollection
    @CollectionTable(name = "user_cart_wines", joinColumns = @JoinColumn(name = "user_cart_id"))
    @MapKeyJoinColumn(name = "wine_id")
    @Column(name = "quantity")
    private Map<CraftWine, Short> winesWithQuantity;


}
