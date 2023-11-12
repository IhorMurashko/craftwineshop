package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.OrderStatus;
import com.craftWine.shop.models.abstracts.AbstractOrderClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users_orders")
public class Order extends AbstractOrderClass {

    public Order() {
        super();
        isPaid = false;
        status = OrderStatus.AWAITING_CONFIRMATION;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal sum;

    @CreatedDate
    private String orderCreated;

    @LastModifiedDate
    private String LastModifiedDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private boolean isPaid;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    private String deliveryAddress;

    private String userComment;
}
