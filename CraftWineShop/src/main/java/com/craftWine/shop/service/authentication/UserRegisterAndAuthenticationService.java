package com.craftWine.shop.service.authentication;

import com.craftWine.shop.dto.authUserDTO.CredentialsDTO;
import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.security.token.ConfirmationToken;
import com.craftWine.shop.security.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserRegisterAndAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserRegisterAndAuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,
                                                TokenProvider tokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder,
                                                TokenProvider tokenProvider1, UserDetailsService userDetailsService,
                                                ConfirmationTokenService ConfirmationTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider1;
        this.userDetailsService = userDetailsService;
        this.confirmationTokenService = ConfirmationTokenService;
    }

    /**
     * Authenticates a user based on the provided credentials.
     * <p>
     * This method attempts to authenticate a user by using the provided email and password in the {@code credentialsDTO}.
     * If the authentication is successful, a token is generated for the authenticated user.
     *
     * @param credentialsDTO A {@code CredentialsDTO} object containing the user's email and password for authentication.
     * @return A token representing the authenticated user.
     * @throws Exception If an authentication-related exception occurs.
     *                   Possible exceptions include:
     *                   <ul>
     *                      <li>{@link DisabledException} - Thrown when the user account is disabled.</li>
     *                      <li>{@link BadCredentialsException} - Thrown when the credentials are invalid.</li>
     *                   </ul>
     */
    public String authenticate(CredentialsDTO credentialsDTO) throws Exception {
        try {


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDTO.email().toLowerCase(), credentialsDTO.password()));
            return tokenProvider.provideToken((User) userDetailsService.loadUserByUsername(credentialsDTO.email().toLowerCase()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * Registers a new user and initiates the account confirmation process.
     * <p>
     * This method takes a {@code User} object, encodes the user's password, saves the user to the repository,
     * generates a confirmation token, and saves the token to enable account confirmation.
     *
     * @param user The {@code User} object representing the new user to be registered.
     * @return A unique token associated with the confirmation process.
     */
    public String signUpUser(User user) {
        // Encode the user's password using BCrypt
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        // Set the encoded password in the user object
        user.setPassword(encodedPassword);

        // Save the user to the repository
        userRepository.save(user);

        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Create a confirmation token with the generated token, current time, expiration time, and associated user
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                user
        );

        // Save the confirmation token
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // Return the generated token
        return token;
    }

    /**
     * Enables a user account based on the provided email.
     * <p>
     * This method is typically called after a user has confirmed their account using a valid confirmation token.
     *
     * @param email The email associated with the user account to be enabled.
     * @return The number of updated records (should be 1 if the user is successfully enabled, 0 otherwise).
     */
    public int enableUser(String email) {
        // Call the repository method to enable the user based on the provided email
        return userRepository.enableUser(email.toLowerCase());
    }


}
