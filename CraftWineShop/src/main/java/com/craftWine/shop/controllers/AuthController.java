package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.authUserDTO.CredentialsDTO;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.service.authentication.UserRegisterAndAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "аутентифікація та авторизація")
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserRegisterAndAuthenticationService userService;
    private final TokenProvider tokenProvider;

    @Operation(
            summary = "авторизація та аутентифікація користувача",
            description = "авторизація та аутентифікація користувача на порталі, перевірка прав доступу",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "400", description = "якщо користувач намагається повторно пройти авторизацію," +
                            "але вже має дійсний токен; " +
                            "якщо користувача не було знайдено в БД або ввів не дійсний пароль/емайл; " +
                            "якщо щось пішло не так"),
                    @ApiResponse(responseCode = "200", description = "при вдалій аутентифікації",
                            headers = {@Header(name = "Authorization", description = "JWT token", schema = @Schema(type = "string"))})
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginController(@Parameter(name = "сутність для передачі даних для аутентифікації",
            required = true, description = "дані для проведення авторизації, " +
            "які попередньо валідуються на відповідність обмеженням")
                                                  @Valid @RequestBody CredentialsDTO credentialsDTO,
                                                  @Parameter(name = "header Authorization",
                                                          description = "перевіряє заголовок Authorization на наявність токена",
                                                          required = false)
                                                  @RequestHeader(name = "Authorization", required = false) String headerToken) {
        try {

            if (headerToken != null &&
                    headerToken.startsWith("Bearer ") &&
                    tokenProvider.validateToken(headerToken.substring(7))) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You have already logged in");
            }


            String token = userService.authenticate(credentialsDTO);


            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth(token);

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(header).build();

        } catch (Exception runtimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");

        }
    }

}
