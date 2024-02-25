package com.craftWine.shop.service.userServices;

import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

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

    @Override
    public Optional<User> extractUserFromToken(String token) {

        if (token == null) {
            throw new IllegalArgumentException("Could not extract email");
        }

        String email = tokenProvider.extractUsername(token.substring(7));

        return findUserByEmail(email);
    }


}
