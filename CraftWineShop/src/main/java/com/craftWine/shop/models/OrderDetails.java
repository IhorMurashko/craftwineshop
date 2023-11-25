package com.craftWine.shop.models;

import com.craftWine.shop.models.abstracts.AbstractOrderDetailsClass;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_details")
public class OrderDetails extends AbstractOrderDetailsClass {

    public OrderDetails() {
        super();
    }

    @OneToOne
    private CraftWine wine;

    private short quantity;
    private BigDecimal sum;


    @ManyToOne
    private Order order;
}
