package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.OrderStatus;
import com.craftWine.shop.models.abstracts.AbstractOrderClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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


    public Order(User user, String deliveryAddress, String userComment) {
        super();
        this.user = user;
        this.deliveryAddress = deliveryAddress;
        this.userComment = userComment;
        this.sum = new BigDecimal(0);
        this.orderCreated = LocalDateTime.now();
        isPaid = false;
        status = OrderStatus.AWAITING_CONFIRMATION;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal sum;

    private LocalDateTime orderCreated;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private boolean isPaid;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    private String deliveryAddress;

    private String userComment;
}
