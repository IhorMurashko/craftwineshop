package com.craftWine.shop.authentication;

import com.craftWine.shop.email.EmailSender;
import com.craftWine.shop.exceptions.EmailProblem;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.utils.EmailBuilder;
import com.craftWine.shop.utils.RandomPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {


    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> resetUserPassword(String userEmail) {

        boolean emailIsPresent = userRepository.findUserByEmail(userEmail).isPresent();


        if (emailIsPresent) {


            String newRandomPassword = RandomPasswordGenerator.generateRandomPassword();


            userRepository.resetUserPassword(userEmail, passwordEncoder.encode(newRandomPassword));


//            emailSender.send(userEmail,
//                    buildEmail(userEmail, newRandomPassword), "Your new password"
//            );

            emailSender.send(
                    userEmail,
                    EmailBuilder.emailResetPasswordBuilder(newRandomPassword),
                    "Your new password"
            );

            return ResponseEntity.ok("New password sent to your email");
        } else {
            throw new EmailProblem("Email not found");
        }


    }


}
