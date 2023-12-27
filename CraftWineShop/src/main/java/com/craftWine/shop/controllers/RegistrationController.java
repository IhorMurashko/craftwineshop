package com.craftWine.shop.controllers;

import com.craftWine.shop.authentication.RegistrationService;
import com.craftWine.shop.authentication.ResetPasswordService;
import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code RegistrationController} class handles HTTP requests related to user registration and password reset.
 * It uses {@link RegistrationService} and {@link ResetPasswordService} to perform the corresponding operations.
 *
 * @author Ihor Murashko
 * @version 1.0
 * @since 2023-12-17
 */
@RequiredArgsConstructor
@CrossOrigin
@Validated
@RestController
@Tag(name = "авторизація та аутентифікація", description = "реєстрація, підтвердження реєстрації," +
        "авторизація, відновлення паролю")
@RequestMapping("/api/v1/reg")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ResetPasswordService resetPasswordService;

    /**
     * Handles the POST request for the "/registration" endpoint.
     * Creates a new user account or resends an email letter to confirm an account.
     *
     * @param registerDTO Represents information about the registering user, including:
     *                    - email
     *                    - password
     *                    - confirmationPassword
     *                    - phoneNumber
     *                    - firstName
     *                    - lastName
     * @return HTTP status CREATED and messaged, OK and message,
     * or CONFLICT and message.
     * @throws InvalidConfirmationPasswordException If the user's password and confirmation password do not match.
     * @throws EmailProblemException                If the user's email has already been confirmed and is ready for use.
     */
    @Operation(summary = "руєстрація нового кристовича",
            description = "отримує інформацію для валідації і у випадку успішної валідації реєструє нового кристовича",
            responses = {
                    @ApiResponse(responseCode = "201", description = "created"),
                    @ApiResponse(responseCode = "409", description = "This account has already been enabled"),
                    @ApiResponse(responseCode = "200")

            })
    @PostMapping(value = "/registration"
    )
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return registrationService.register(registerDTO);
    }

    /**
     * Handles the GET request for the "/reset_password" endpoint.
     * Initiates the password reset process for the user with the specified email address.
     *
     * @param userEmail The email address of the user whose password needs to be reset.
     * @return ResponseEntity indicating the success of the password reset operation.
     * @throws EmailProblemException    If the email address is not found in the UserRepository.
     * @throws IllegalArgumentException if the user is trying to reset the password more than once of 24 hours.
     */
    @Hidden
    @GetMapping("/reset_password")
    public ResponseEntity<String> rememberThePassword(@RequestParam("email") String userEmail) {
        // Delegates the password reset operation to the ResetPasswordService.
        return resetPasswordService.resetUserPassword(userEmail);
    }

    /**
     * Handles the GET request for the "/confirm" endpoint.
     * Confirms the user registration using the provided token.
     *
     * @param token The token used for confirming user registration.
     * @return ResponseEntity with HTTP status indicating the result of the confirmation.
     */
    @Hidden
    @GetMapping(path = "/confirm")
    public ResponseEntity<HttpStatus> confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
