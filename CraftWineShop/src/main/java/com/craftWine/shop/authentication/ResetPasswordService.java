package com.craftWine.shop.authentication;

import com.craftWine.shop.email.EmailSender;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.utils.CheckHoursBetweenTwoDates;
import com.craftWine.shop.utils.EmailBuilder;
import com.craftWine.shop.utils.RandomPasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * The {@code ResetPasswordService} class provides functionality for resetting user passwords.
 * It uses a UserRepository for user data, an EmailSender for sending emails, and a PasswordEncoder for encoding passwords.
 *
 * @author Ihor Murashko
 * @version 1.0
 * @since 2023-12-17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    /**
     * Resets the password for the user with the given email address.
     *
     * @param userEmail The email address of the user whose password needs to be reset.
     * @return ResponseEntity indicating the success of the password reset operation.
     * @throws EmailProblemException    if the email address is not found in the UserRepository.
     * @throws IllegalArgumentException if the user is trying to reset the password more than once of 24 hours.
     */
    public ResponseEntity<String> resetUserPassword(String userEmail) {

        // Check if the email address is present in the UserRepository.
        boolean emailIsPresent = userRepository.findUserByEmail(userEmail).isPresent();
        boolean isTimeBetweenTwoDatesIsMoreThan23Hours = CheckHoursBetweenTwoDates
                .isTimeBetweenTwoDatesIsMoreThan23Hours(userRepository.lastTimeResetPassword(userEmail));
//        boolean isTimeBetweenTwoDatesIsMoreThan23Hours = isTimeBetweenTwoDatesIsMoreThan23Hours(userEmail);

        if (!isTimeBetweenTwoDatesIsMoreThan23Hours) {

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String exceptionMessage = "You can reset your password only one time during 24 hours." +
                    "You could try reset you password " + userRepository.lastTimeResetPassword(userEmail).get().plusDays(1)
                    .format(timeFormatter);

            log.info(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        // Check if the user is enabled.
        if (emailIsPresent && userRepository.isEnabled(userEmail)) {


            // Generate a new random password.
            String newRandomPassword = RandomPasswordGenerator.generateRandomPassword();

            // Reset the user's password in the UserRepository with the encoded new password.
            userRepository.resetUserPassword(userEmail, passwordEncoder.encode(newRandomPassword));
            userRepository.updateUserLastTimeResetPassword(userEmail, LocalDateTime.now());

            // Send an email to the user with the new password.
            emailSender.send(
                    userEmail,
                    EmailBuilder.emailResetPasswordBuilder(newRandomPassword),
                    "Your new password"
            );

            // Return a ResponseEntity indicating success.
            return ResponseEntity.ok("New password sent to your email");
        } else {
            // If the email address is not found, throw an EmailProblemException.
            throw new EmailProblemException("Email not found or your account is not availed " + userEmail);
        }
    }
}