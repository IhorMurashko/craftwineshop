package com.craftWine.shop.service.userServices;

import com.craftWine.shop.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {

    void saveUser(User user);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);


}
