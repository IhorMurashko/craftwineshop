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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    private RegisterDTO registerDTO;

    @BeforeEach
    void initRegisterDTo() {
        this.registerDTO = new RegisterDTO("eamil@gmail.com", "password",
                "password", "0123456789012", "name", "surname");
    }


    @Test
    @DisplayName("user's password and confirmation password do not match during the registration process")
    void throwInvalidConfirmationPasswordExceptionWhen_userPasswordAndConfirmationPasswordIsDifferent() {

        RegisterDTO registerDto = new RegisterDTO("XXXXXXXXXXXXXXX", "password", "different password",
                "0123456789012", "name", "surname");


        RuntimeException exception = assertThrows(InvalidConfirmationPasswordException.class,
                () -> registrationService.register(registerDto));


        assertEquals("password didn't confirm", exception.getMessage());

        assertEquals(InvalidConfirmationPasswordException.class, exception.getClass());

        verifyNoInteractions(userRepository, userRegisterAndAuthenticationService, emailSender);
    }

    @Test
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
        verify(emailSender, times(1)).send(anyString(), anyString(),anyString());

        assertEquals(registerDTO.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verifyNoMoreInteractions(userRepository, emailSender);
        verifyNoInteractions(userRegisterAndAuthenticationService);

    }


    @Test
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
    void confirmToken() {
    }
}