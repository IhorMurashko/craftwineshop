package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.UserRole;
import com.craftWine.shop.models.abstracts.AbstractUserClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")

public class User extends AbstractUserClass {


    public User() {
        super();
    }


    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "locked")
    private Boolean locked = false;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @OneToOne(
//            mappedBy = "user",
            cascade = CascadeType.ALL)
    private Bucket bucket;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "users_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "craft_wine_id")
    )
    private List<CraftWine> favorites;


}
