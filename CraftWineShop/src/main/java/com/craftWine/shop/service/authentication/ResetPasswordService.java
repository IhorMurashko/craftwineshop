package com.craftWine.shop.service.authentication;

import com.craftWine.shop.enumTypes.NotificationType;
import com.craftWine.shop.notificationSender.EmailNotificationSenderImpl;
import com.craftWine.shop.notificationSender.Notification;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.notificationSender.NotificationSenderFactory;
import com.craftWine.shop.notificationSender.NotificationSenderStrategy;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.utils.CheckHoursBetweenTwoDates;
import com.craftWine.shop.utils.EmailBuilder;
import com.craftWine.shop.utils.RandomPasswordGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private final PasswordEncoder passwordEncoder;
    private final NotificationSenderFactory notificationSenderFactory;
    private final NotificationSenderStrategy notificationSenderStrategy;

    /**
     * Resets the password for the user with the given email address.
     *
     * @param userEmail The email address of the user whose password needs to be reset.
     * @return ResponseEntity indicating the success of the password reset operation.
     * @throws EmailProblemException    if the email address is not found in the UserRepository.
     * @throws IllegalArgumentException if the user is trying to reset the password more than once of 23 hours.
     */
    public ResponseEntity<String> resetUserPassword(String userEmail) {

        @NotBlank(message = "Email field can't be blank")
        @Email(message = "Email pattern is not correct", regexp = ".+@.+\\..+")
        String email = userEmail.toLowerCase();


        // Check if the email address is present in the UserRepository.
        boolean emailIsPresent = userRepository.findUserByEmail(email).isPresent();
        boolean isTimeBetweenTwoDatesIsMoreThan23Hours = CheckHoursBetweenTwoDates
                .isTimeBetweenTwoDatesIsMoreThan23Hours(userRepository.lastTimeResetPassword(email));

        if (!isTimeBetweenTwoDatesIsMoreThan23Hours) {

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String exceptionMessage = "You can reset your password only one time during 24 hours." +
                    "You could try reset you password " + userRepository.lastTimeResetPassword(email).get().plusDays(1)
                    .format(timeFormatter);

            log.info(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
        // Check if the user is enabled.
        if (emailIsPresent && userRepository.isEnabled(email)) {


            // Generate a new random password.
            String newRandomPassword = RandomPasswordGenerator.generateRandomPassword();

            // Reset the user's password in the UserRepository with the encoded new password.
            userRepository.resetUserPassword(email, passwordEncoder.encode(newRandomPassword));
            userRepository.updateUserLastTimeResetPassword(email, LocalDateTime.now());

            // Email the user with the new password.
            Notification newPasswordNotification = Notification.builder()
                    .addressee(email)
                    .subject("Your new password")
                    .text(EmailBuilder.emailResetPasswordBuilder(newRandomPassword))
                    .build();

            notificationSenderStrategy.sendNotification(notificationSenderFactory.factory(NotificationType.EMAIL),
                    newPasswordNotification);


            // Return a ResponseEntity indicating success.
            return ResponseEntity.ok("New password sent to your email");
        } else {
            // If the email address is not found, throw an EmailProblemException.
            throw new EmailProblemException("Email not found or your account is not availed " + email);
        }
    }
}