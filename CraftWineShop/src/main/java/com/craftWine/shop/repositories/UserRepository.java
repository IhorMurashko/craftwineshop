package com.craftWine.shop.repositories;

import com.craftWine.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsUserByEmail(String email);

    boolean existsUserById(Long id);

    Optional<User> findUserByEmail(String email);

    @Query("SELECT us.lastTimeResetPassword FROM User us WHERE us.email =:email")
    Optional<LocalDateTime> lastTimeResetPassword(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User us SET us.lastTimeResetPassword =?2 WHERE us.email =?1")
    void updateUserLastTimeResetPassword(String email, LocalDateTime now);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.password = ?2 WHERE a.email = ?1")
    void resetUserPassword(String email, String password);

    @Query("select us.enabled from User us where us.email=?1 ")
    boolean isEnabled(String email);

}
