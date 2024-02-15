package com.craftWine.shop.repositories;

import com.craftWine.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsUserByEmail(String email);

    boolean existsUserById(Long id);

    Optional<User> findUserByEmail(String email);

    @Query("SELECT us.lastTimeResetPassword FROM User us WHERE us.email=:email")
    Optional<LocalDateTime> lastTimeResetPassword(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE User us SET us.lastTimeResetPassword =:now WHERE us.email =:email")
    void updateUserLastTimeResetPassword(@Param("email") String email, @Param("now") LocalDateTime now);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email=:email")
    int enableUser(@Param("email") String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.password =:password WHERE a.email=:email")
    void resetUserPassword(@Param("email") String email, @Param("password") String password);

    @Query("select us.enabled from User us where us.email=:email")
    boolean isEnabled(@Param("email") String email);

}
