package com.craftWine.shop.authentication;

import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.email.EmailSender;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.token.ConfirmationToken;
import com.craftWine.shop.security.token.ConfirmationTokenService;
import com.craftWine.shop.utils.SwitchCaseToCapitalize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class RegistrationServiceTest {


    @Mock
    private UserRegisterAndAuthenticationService userRegisterAndAuthenticationService;
    @Mock
    private EmailSender emailSender;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private RegistrationService registrationService;
    @Captor
    private ArgumentCaptor<RegisterDTO> registerDTOArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    private User user;
    private RegisterDTO registerDTO;
    private String token;

    @BeforeEach
    void initRegisterDTO() {
        this.registerDTO = new RegisterDTO("eamil@gmail.com", "password",
                "password", "0123456789012", "name", "surname");
        this.token = UUID.randomUUID().toString();
        this.user = new User("test@email.com", "password", "012345678912", "name", "surname");
    }


    @Test
    @Tag("registration")
    @DisplayName("user's password and confirmation password do not match during the registration process")
    void throwInvalidConfirmationPasswordExceptionWhen_userPasswordAndConfirmationPasswordIsDifferent() {

        RegisterDTO registerDto = new RegisterDTO("XXXXXXXXXXXXXXX", "password", "different password",
                "0123456789012", "name", "surname");


        RuntimeException exception = assertThrows(InvalidConfirmationPasswordException.class,
                () -> registrationService.register(registerDto));


        assertEquals("password don't match", exception.getMessage());

        assertEquals(InvalidConfirmationPasswordException.class, exception.getClass());

        verifyNoInteractions(userRepository, userRegisterAndAuthenticationService, emailSender);
    }

    @Test
    @Tag("registration")
    @DisplayName("EmailProblemException when User email not found")
    void throwEmailProblemWhen_userEmailNotFound() {

        doThrow(new EmailProblemException("Could not find account with email " + registerDTO.getEmail()))
                .when(userRepository).findUserByEmail(registerDTO.getEmail());


        RuntimeException exception = assertThrows(EmailProblemException.class, () ->
                registrationService.register(registerDTO));


        verify(userRepository, times(1)).findUserByEmail(registerDTO.getEmail());

        assertEquals(EmailProblemException.class, exception.getClass());
        assertEquals("Could not find account with email " + registerDTO.getEmail(), exception.getMessage());


        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userRegisterAndAuthenticationService, emailSender);
    }


    @Test
    @Tag("registration")
    @DisplayName("return status OK when user hasn't enabled account")
    void statusOKWhen_userIsNotEnabled() {

        User user = new User(
                registerDTO.getEmail(),
                registerDTO.getPassword(),
                registerDTO.getPhoneNumber(),
                SwitchCaseToCapitalize.switchCaseToCapitalize(registerDTO.getFirstName()),
                SwitchCaseToCapitalize.switchCaseToCapitalize(registerDTO.getLastName())
        );

        user.setId(1);
        user.setEnabled(false);


        doReturn(Optional.of(user)).when(this.userRepository).findUserByEmail(registerDTO.getEmail());
        doReturn(Optional.of(new ConfirmationToken())).when(confirmationTokenService).getConfirmationTokenByUserID(user.getId());


        ResponseEntity<String> response = registrationService.register(registerDTO);
        verify(userRepository, times(2)).findUserByEmail(stringArgumentCaptor.capture());


        verify(userRepository, times(2)).findUserByEmail(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());

        assertEquals(registerDTO.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verifyNoMoreInteractions(userRepository, emailSender);
        verifyNoInteractions(userRegisterAndAuthenticationService);

    }


    @Test
    @Tag("registration")
    @DisplayName("return status CONFLICT when user has already enabled")
    void statusCONFLICTWhen_userIsAlreadyRegistered() {

        User user = new User(
                registerDTO.getEmail(),
                registerDTO.getPassword(),
                registerDTO.getPhoneNumber(),
                SwitchCaseToCapitalize.switchCaseToCapitalize(registerDTO.getFirstName()),
                SwitchCaseToCapitalize.switchCaseToCapitalize(registerDTO.getLastName())
        );

        user.setEnabled(true);


        doReturn(Optional.of(user)).when(this.userRepository).findUserByEmail(registerDTO.getEmail());


        ResponseEntity<String> response = registrationService.register(registerDTO);
        verify(userRepository, times(2)).findUserByEmail(stringArgumentCaptor.capture());


        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("This account has already been enabled", response.getBody());
        assertEquals(registerDTO.getEmail(), stringArgumentCaptor.getValue());

        verify(userRepository, times(2)).findUserByEmail(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userRegisterAndAuthenticationService, emailSender);


    }


    @Test
    @Tag("registration")
    @DisplayName("return status created when user register first time")
    void returnStatusCreatedWhen_userRegisterFirstTime() {

        doReturn(Optional.empty()).when(userRepository).findUserByEmail(registerDTO.getEmail());

        doReturn(UUID.randomUUID().toString()).when
                (userRegisterAndAuthenticationService).signUpUser(any());

        doNothing().when(emailSender).send(anyString(), anyString(), anyString());

        ResponseEntity<String> response = registrationService.register(registerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("created", response.getBody());

        verify(userRepository, times(1)).findUserByEmail(any());

        verify(userRegisterAndAuthenticationService, times(1)).signUpUser(any());
        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());


        verifyNoMoreInteractions(userRepository, userRegisterAndAuthenticationService, emailSender);


    }


    @Test
    @Tag("confirmationToken")
    @DisplayName("get IllegalArgumentException when token not found by user email")
    void tokenNotFound() {

//        String token = UUID.randomUUID().toString();

        doReturn(Optional.empty()).when(confirmationTokenService).getToken(token);


        RuntimeException exception = assertThrows(IllegalStateException.class, () ->
                registrationService.confirmToken(token));


        assertEquals(IllegalStateException.class, exception.getClass());
        assertEquals("Token not found", exception.getMessage());

        verify(confirmationTokenService, times(1)).getToken(token);
        verify(confirmationTokenService, times(1)).getToken(anyString());
        verifyNoMoreInteractions(confirmationTokenService);
        verifyNoInteractions(userRegisterAndAuthenticationService);
    }

    @Test
    @Tag("confirmationToken")
    @DisplayName("get EmailProblemException when user by the email has been confirmed")
    void confirmedAtNotNull() {

//        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now().minusHours(12),
                LocalDateTime.now().plusHours(12), new User());

        confirmationToken.setConfirmedAt(LocalDateTime.now());


        doReturn(Optional.of(confirmationToken)).when(confirmationTokenService).getToken(token);


        RuntimeException exception = assertThrows(EmailProblemException.class, () ->
                registrationService.confirmToken(token));
        verify(confirmationTokenService).getToken(stringArgumentCaptor.capture());


        assertEquals(token, stringArgumentCaptor.getValue());

        assertEquals(EmailProblemException.class, exception.getClass());
        assertEquals("Email already confirmed", exception.getMessage());

        verify(confirmationTokenService, times(1)).getToken(anyString());
        verifyNoMoreInteractions(confirmationTokenService);
        verifyNoInteractions(userRegisterAndAuthenticationService);

    }


    @Test
    @Tag("confirmationToken")
    @DisplayName("get IllegalStateException when token expired")
    void tokenExpired() {

//        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now().minusHours(48),
                LocalDateTime.now().minusHours(24), new User());

        doReturn(Optional.of(confirmationToken)).when(confirmationTokenService).getToken(token);


        RuntimeException exception = assertThrows(IllegalStateException.class, () ->
                registrationService.confirmToken(token));
        verify(confirmationTokenService).getToken(stringArgumentCaptor.capture());


        assertEquals(IllegalStateException.class, exception.getClass());
        assertEquals("Token expired", exception.getMessage());
        assertEquals(token, stringArgumentCaptor.getValue());

        verify(confirmationTokenService, times(1)).getToken(anyString());
        verifyNoMoreInteractions(confirmationTokenService);
        verifyNoInteractions(userRegisterAndAuthenticationService);

    }


    @Test
    @Tag("confirmationToken")
    @DisplayName("success confirmed token")
    void successConfirmedToken() {

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now().minusHours(12),
                LocalDateTime.now().plusHours(12), user);

        doReturn(Optional.of(confirmationToken)).when(confirmationTokenService).getToken(token);

        doReturn(1).when(confirmationTokenService).setConfirmedAt(token);

        doReturn(1).when(userRegisterAndAuthenticationService).enableUser(user.getEmail());

        ResponseEntity<HttpStatus> response = registrationService.confirmToken(token);
        verify(userRegisterAndAuthenticationService).enableUser(stringArgumentCaptor.capture());

        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
        assertEquals(user.getEmail(), confirmationToken.getUser().getEmail());
        assertEquals(confirmationToken.getUser().getEmail(), stringArgumentCaptor.getValue());

        verify(confirmationTokenService, times(1)).getToken(anyString());
        verify(confirmationTokenService, times(1)).setConfirmedAt(anyString());
        verifyNoMoreInteractions(confirmationTokenService);

        verify(userRegisterAndAuthenticationService, times(1)).enableUser(anyString());


    }


}