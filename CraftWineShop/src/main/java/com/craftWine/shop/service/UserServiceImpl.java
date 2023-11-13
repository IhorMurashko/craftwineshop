package com.craftWine.shop.service;

import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.models.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    @Autowired
    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {

        userRepository.save(user);
    }

    @Override
    public boolean existsUserByEmail(String email) {

        return userRepository.existsUserByEmail(email);
    }

}
