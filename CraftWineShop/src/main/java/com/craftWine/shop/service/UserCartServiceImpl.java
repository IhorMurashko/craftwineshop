package com.craftWine.shop.service;

import com.craftWine.shop.models.UserCart;
import com.craftWine.shop.repositories.UserCartRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class UserCartServiceImpl implements UserCartService {

    private final UserCartRepository userCartRepository;

    @Override
    public void save(UserCart userCart) {
        userCartRepository.save(userCart);

    }
}
