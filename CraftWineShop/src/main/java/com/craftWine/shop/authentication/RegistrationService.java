package com.craftWine.shop.authentication;

import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.email.EmailSender;
import com.craftWine.shop.exceptions.EmailProblem;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.token.ConfirmationToken;
import com.craftWine.shop.security.token.ConfirmationTokenService;
import com.craftWine.shop.utils.EmailBuilder;
import com.craftWine.shop.utils.SwitchCaseToCapitalize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRegisterAndAuthenticationService userService;
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;


    /**
     * Performs the logic for user registration and checks the provided registration information.
     *
     * @param requestDTO Represents information about the registering user, including:<br>
     *                   - email<br>
     *                   - password<br>
     *                   - confirmationPassword<br>
     *                   - phoneNumber<br>
     *                   - deliveryAddress<br>
     *                   - firstName<br>
     *                   - lastName<br>
     * @return A User token and information about the successful email letter sent for confirming registration.
     * @throws InvalidConfirmationPasswordException If the user's password and confirmation password do not match,
     * @throws EmailProblem             or if the user's email has already been confirmed and is ready for use.
     */
//    public String register(RegisterDTO requestDTO) {
    public ResponseEntity<String> register(RegisterDTO requestDTO) {

        // Check if the user's password and confirmation password do not match during the registration process.
        if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmationThePassword())) {
            throw new InvalidConfirmationPasswordException("password didn't confirm");
        }

        // Check if the user already exists based on the provided email.
        boolean userExists = userRepository
                .findUserByEmail(requestDTO.getEmail())
                .isPresent();
        // The link to be included in the confirmation email.
        String link = "http://localhost:8080/confirm?token=";

        /*
         * If the user already exists and has confirmed their account, a message will be returned.
         * If the user already exists but hasn't confirmed their account, a new email letter with
         * a link for confirming their account will be sent.
         */
        if (userExists) {

            User user = userRepository.findUserByEmail(requestDTO.getEmail()).orElseThrow(
                    () -> new EmailProblem("Could not find account with email " + requestDTO.getEmail()));

            return user.getEnabled()
                    ? ResponseEntity.status(HttpStatus.CONFLICT).body("The account has already been enabled")
                    : ResponseEntity.status(HttpStatus.OK).body(sendConfirmationLetterAgain(requestDTO.getEmail(), requestDTO.getFirstName(), user.getId(), link));


        }

        User user = new User(
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                requestDTO.getPhoneNumber(),
                SwitchCaseToCapitalize.switchCaseToCapitalize(requestDTO.getFirstName()),
                SwitchCaseToCapitalize.switchCaseToCapitalize(requestDTO.getLastName())
        );

        // If the user does not exist, register the new user and send a confirmation email.
        String token = userService.signUpUser(user);

        // Return the token associated with the registration process.
//        emailSender.send(
//                requestDTO.getEmail(),
//                buildEmail(requestDTO.getEmail(), link + token), "Confirm your email");
        emailSender.send(user.getEmail(),
                EmailBuilder.emailRegistrationBuilder(user.getFirstName(), link + token),
                "Confirm your email");

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


    /**
     * This method is called when a user tries to register a new account, but their email already exists and hasn't been confirmed by email.
     * They will receive a new email letter with a link for confirming their account.
     *
     * @param email  The email address to which the confirmation email with a link will be sent.
     * @param userId The user ID used to check if the user is already confirmed.
     * @param link   The link for confirmation.
     * @return The token associated with the confirmation process.
     * @throws EmailProblem If there is an issue with sending the confirmation email.
     */
    private String sendConfirmationLetterAgain(String email, String name, long userId, String link) {


        // Checking if the user is already confirmed and if the email already exists.
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationTokenByUserID(userId).orElseThrow(() ->
                        new EmailProblem("Email not found"));

        confirmationToken.setExpiresAt(
                LocalDateTime.now().plusDays(1)
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        emailSender.send(email,
                EmailBuilder.emailRegistrationBuilder(name, link + confirmationToken.getToken()),
                "Confirm your email again");

        return confirmationToken.getToken();
    }

    /**
     * method called when a user activates a link from their email letter to confirm their account by email.
     *
     * @param token The token obtained from the GET method link, used to check for confirmation.
     * @return A message about successful confirmation.
     * @throws IllegalStateException If the user is already confirmed, the confirmation date has already expired, or the token wasn't found.
     */
    @Transactional
    public String confirmToken(String token) {


        // get token from database
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        // If the date of confirmation is not null, throw an exception with the message "Email already confirmed."
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailProblem("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        // If the expiration date is earlier than the current time, throw an exception with the message "Token expired."
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        // make the user's field "enable" true using his email
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}