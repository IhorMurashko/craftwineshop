package com.craftWine.shop.service.userServices;

import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id) {

        return userRepository.findById(id);
    }


}
