package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.userDTO.UserDTO;
import com.craftWine.shop.mapper.UserMapper;
import com.craftWine.shop.models.User;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.service.userServices.UserService;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserPersonalController {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            description = "отримати користувача",
            method = "GET",
            responses = {
                    @ApiResponse(responseCode = "200", description = "інформація про користувача " +
                            "з отриманого токену")}

    )

    @GetMapping(value = "/get_user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserInformation(
            @Parameter(required = true, description = "токен в заголовці Authorization", example = "Bearer eyJhbGciOiJIUzUxMiJ9." +
                    "eyJzdWIiOiJ0aGlzQGVtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWQiOjEsImV4cCI6MTcxMDYxNzE5NiwiaWF0IjoxNzEwNTMwNzk2LCJlbWFpbCI6InRoaXNAZW1haWwuY29tIn0" +
                    ".xQViqf8XnVGuQjcdK9tSxgCbb0kMvZdXv48-PD4Z7TeGFVcSrkdnV4MDGB8yshCVJypCZR0u-na_dSJARl7Z_w")
            @RequestHeader(name = "Authorization") String headerToken) {

        if (headerToken != null &&
                headerToken.startsWith("Bearer ") &&
                tokenProvider.validateToken(headerToken.substring(7))) {

            User user = userService.extractUserFromToken(headerToken)
                    .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));

            UserDTO userDTO = userMapper.toDTO(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(userDTO);


        } else {
            throw new JwtException("Invalid authorization");
        }
    }

}
