package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.UserRole;
import com.craftWine.shop.models.abstracts.AbstractUserClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")

public class User extends AbstractUserClass implements UserDetails {
//TODO: JSONIGNORE!!!

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "enabled")
    private Boolean enabled;

    private LocalDateTime lastTimeResetPassword;

    @OneToOne(
//            mappedBy = "user",
            cascade = CascadeType.ALL)
    private UserCart userCart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    @ManyToMany
    @JoinTable(name = "users_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "craft_wine_id")
    )
    private List<CraftWine> favorites;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WineComment> wineComments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WineEvaluation> wineEvaluations;

    public User() {
        super();
    }


    public User(String email, String password, String phoneNumber,
                String firstName, String lastName) {
        super();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = UserRole.USER;
        this.enabled = false;
        this.locked = false;
        this.userCart = new UserCart(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.getRole().name()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
