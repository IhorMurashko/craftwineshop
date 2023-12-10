package com.craftWine.shop.controllers;

import com.craftWine.shop.authentication.RegistrationService;
import com.craftWine.shop.authentication.ResetPasswordService;
import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@Validated
@RestController
@RequestMapping("/reg")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ResetPasswordService resetPasswordService;

    /**
     * This method uses a {@link com.craftWine.shop.authentication.RegistrationService} to create a new account
     * or resend an email letter to confirm an account.
     *
     * @param registerDTO Represents information about the registering user, including:<br>
     *                    - email<br>
     *                    - password<br>
     *                    - confirmationPassword<br>
     *                    - phoneNumber<br>
     *                    - deliveryAddress<br>
     *                    - firstName<br>
     *                    - lastName<br>
     * @return User token and information about the successful email letter sent for confirming registration.
     * @throws InvalidConfirmationPasswordException If the user's password and confirmation password do not match,
     * @throws IllegalArgumentException             or if the user's email has already been confirmed and is ready for use.
     */
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {

        return registrationService.register(registerDTO);
    }


    @GetMapping("/reset_password")
    public ResponseEntity<String> rememberThePassword(@RequestParam("email") String userEmail) {
        return resetPasswordService.resetUserPassword(userEmail);
    }


}
