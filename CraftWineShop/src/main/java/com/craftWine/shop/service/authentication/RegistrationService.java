package com.craftWine.shop.service.authentication;

import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import com.craftWine.shop.models.User;
import com.craftWine.shop.notificationSender.Notification;
import com.craftWine.shop.notificationSender.NotificationSenderFactory;
import com.craftWine.shop.notificationSender.NotificationSenderStrategy;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.token.ConfirmationToken;
import com.craftWine.shop.security.token.ConfirmationTokenService;
import com.craftWine.shop.utils.EmailBuilder;
import com.craftWine.shop.utils.SwitchCaseToCapitalize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRegisterAndAuthenticationService userRegisterAndAuthenticationService;
    private final NotificationSenderFactory notificationSenderFactory;
    private final NotificationSenderStrategy notificationSenderStrategy;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserRepository userRepository;

    @Value("${backendLink}")
    private String backendLink;

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
     * @throws EmailProblemException                or if the user's email has already been confirmed and is ready for use.
     */
    public ResponseEntity<String> register(RegisterDTO requestDTO) {

        // Check if the user's password and confirmation password do not match during the registration process.
        if (!Objects.equals(requestDTO.getPassword(), requestDTO.getConfirmationThePassword())) {
            throw new InvalidConfirmationPasswordException("password don't match");
        }

        // Check if the user already exists based on the provided email.
        boolean userExists = userRepository
                .findUserByEmail(requestDTO.getEmail().toLowerCase())
                .isPresent();
        // The link to be included in the confirmation email.
        String link = backendLink + "/api/v1/reg/confirm?token=";
//        String link = "http://localhost:8080/api/v1/reg/confirm?token=";

        /*
         * If the user already exists and has confirmed their account, a message will be returned.
         * If the user already exists but hasn't confirmed their account, a new email letter with
         * a link for confirming their account will be sent.
         */
        if (userExists) {

            User user = userRepository.findUserByEmail(requestDTO.getEmail().toLowerCase()).orElseThrow(
                    () -> new EmailProblemException("Could not find account with email " + requestDTO.getEmail().toLowerCase()));

            return user.getEnabled()
                    ? ResponseEntity.status(HttpStatus.CONFLICT).body("This account has already been enabled")
                    : ResponseEntity.status(HttpStatus.OK).body(resendConfirmationLetter(requestDTO, user.getId(), link));
        }

        User user = new User(
                requestDTO.getEmail().toLowerCase(),
                requestDTO.getPassword(),
                requestDTO.getPhoneNumber(),
                SwitchCaseToCapitalize.switchCaseToCapitalize(requestDTO.getFirstName()),
                SwitchCaseToCapitalize.switchCaseToCapitalize(requestDTO.getLastName())
        );

        // If the user does not exist, register the new user and send a confirmation email.
        String token = userRegisterAndAuthenticationService.signUpUser(user);

        Notification registrationNotification = Notification.builder()
                .addressee(user.getEmail().toLowerCase())
                .subject("Confirm your email")
                .text(EmailBuilder.emailRegistrationBuilder(user.getFirstName(), link + token))
                .build();

        // Return the token associated with the registration process.
        notificationSenderStrategy.sendNotification(
                notificationSenderFactory.factory(requestDTO.getNotificationType()),
                registrationNotification);


        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }


    /**
     * This method is called when a user tries to register a new account, but their email already exists and hasn't been confirmed by email.
     * They will receive a new email letter with a link for confirming their account.
     *
     * @param requestDTO The object which contains date like address to which the confirmation email with a link will be sent.
     * @param userId     The user ID used to check if the user is already confirmed.
     * @param link       The link for confirmation.
     * @return The token associated with the confirmation process.
     * @throws EmailProblemException If there is an issue with sending the confirmation email.
     */
    private String resendConfirmationLetter(RegisterDTO requestDTO, long userId, String link) {

        String email = requestDTO.getEmail().toLowerCase();
        String name = requestDTO.getFirstName();


        // Checking if the user is already confirmed and if the email already exists.
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationTokenByUserID(userId).orElseThrow(() ->
                        new EmailProblemException("Email not found"));

        confirmationToken.setExpiresAt(
                LocalDateTime.now().plusDays(1)
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Notification resendNotification = Notification.builder()
                .addressee(email)
                .subject(EmailBuilder.emailRegistrationBuilder(name, link + confirmationToken.getToken()))
                .text("Lets confirm your email")
                .build();


        notificationSenderStrategy.sendNotification(
                notificationSenderFactory.factory(requestDTO.getNotificationType()),
                resendNotification);

        return "letter was sent successfully";
    }


    /**
     * The {@code ConfirmationTokenService} class handles HTTP requests related to confirming user registration tokens.
     * It confirms the token, checks for expiration, and activates the user account.
     *
     * @param token The token to be confirmed.
     * @return ResponseEntity with HTTP status and a redirection location.
     * @throws IllegalStateException If the token is not found, has already been confirmed, or has expired.
     * @throws EmailProblemException If there is an issue with the email confirmation.
     */
    @Transactional
    public ResponseEntity<HttpStatus> confirmToken(String token) {
        // Get token from the database
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        // Check if the confirmation date is not null, indicating that the email is already confirmed
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailProblemException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        // Check if the token has expired
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        // Set the confirmation date for the token
        confirmationTokenService.setConfirmedAt(token);

        // Activate the user by setting the "enabled" field to true using the user's email
        userRegisterAndAuthenticationService.enableUser(confirmationToken.getUser().getEmail());

        // Return a ResponseEntity with a redirection status and location
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/main/craft_wines")).build();
    }

}