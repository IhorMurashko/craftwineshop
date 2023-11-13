package com.craftWine.controllers;

import com.craftWine.dto.UserDTO;
import com.craftWine.service.UserService;
import com.craftWine.shop.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class TestController {

    private final UserService userService;


    @PostMapping("/save_new_user")
    public ResponseEntity<> saveNewUser(UserDTO userDTO) {




        return ResponseEntity.created(user);
    }

}
