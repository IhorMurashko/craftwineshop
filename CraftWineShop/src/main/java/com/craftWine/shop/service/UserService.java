package com.craftWine.shop.service;

import com.craftWine.shop.models.User;

public interface UserService {

    void saveUser(User user);

    boolean existsUserByEmail(String email);

}
