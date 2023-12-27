package com.craftWine.shop.authentication;

import com.craftWine.shop.email.EmailSender;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResetPasswordServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailSender emailSender;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ResetPasswordService resetPasswordService;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;


    private String userEmail;
    private User user;

    @BeforeEach
    void setUp() {
        this.userEmail = "test@email.com";
        this.user = new User(userEmail, "password", "0123456789012", "name", "surname");
    }

    @Test
    @DisplayName("/api/v1/reg/reset_password exception EmailProblemException when email not found")
    void emailProblemExceptionWhen_emailNotFound() {
        doThrow(new EmailProblemException("Email not found or your account is not availed " + userEmail))
                .when(userRepository).findUserByEmail(userEmail);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> resetPasswordService.resetUserPassword(userEmail));
        verify(userRepository).findUserByEmail(stringArgumentCaptor.capture());


        assertEquals(userEmail, stringArgumentCaptor.getValue());
        assertEquals("Email not found or your account is not availed " + userEmail, exception.getMessage());
        assertEquals(EmailProblemException.class, exception.getClass());


        verify(userRepository, times(1)).findUserByEmail(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder, emailSender);

    }


    @Test
    @DisplayName("/api/v1/reg/reset_password exception IllegalArgumentException when user tries to reset password second time during 24 hourse")
    void illegalArgumentExceptionWhen_userTriesToResetPasswordSecondTimeDuring24Hours() {

        LocalDateTime now = LocalDateTime.now();
        doReturn(Optional.of(user)).when(userRepository).findUserByEmail(userEmail);
        doReturn(Optional.of(now)).when(userRepository).lastTimeResetPassword(user.getEmail());

        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> resetPasswordService.resetUserPassword(user.getEmail()));
        verify(userRepository).findUserByEmail(stringArgumentCaptor.capture());


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String exceptionMessage = "You can reset your password only one time during 24 hours." +
                "You could try reset you password " + now.plusDays(1)
                .format(timeFormatter);
        assertEquals(illegalArgumentException.getMessage(), exceptionMessage);

        verify(userRepository, times(1)).findUserByEmail(any());
        verify(userRepository, times(2)).lastTimeResetPassword(any());
        verifyNoMoreInteractions(userRepository);

        verifyNoInteractions(passwordEncoder, emailSender);
    }


    @Test
    @DisplayName("/api/v1/reg/reset_password exception EmailProblemException when user is not enabled ")
    void emailProblemExceptionWhen_userIsNotEnabled() {
        LocalDateTime minusTwoDays = LocalDateTime.now().minusDays(2);
        System.out.println("now is:" + LocalDateTime.now() + " minusTwoDays is: " + minusTwoDays);
        doReturn(Optional.ofNullable(minusTwoDays)).when(userRepository).findUserByEmail(user.getEmail());
        doReturn(Optional.of(minusTwoDays)).when(userRepository).lastTimeResetPassword(user.getEmail());
        doReturn(false).when(userRepository).isEnabled(user.getEmail());

        RuntimeException exception = assertThrows(EmailProblemException.class, () -> resetPasswordService.resetUserPassword(user.getEmail()));
        verify(userRepository).isEnabled(stringArgumentCaptor.capture());


        assertEquals(userEmail, stringArgumentCaptor.getValue());
        assertEquals(EmailProblemException.class, exception.getClass());
        assertEquals("Email not found or your account is not availed " + user.getEmail(), exception.getMessage());

        verify(userRepository, times(1)).findUserByEmail(any());
        verify(userRepository, times(1)).lastTimeResetPassword(user.getEmail());
        verify(userRepository, times(1)).isEnabled(user.getEmail());

        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder, emailSender);

    }

    @Test
    @DisplayName("/api/v1/reg/reset_password get status Ok")
    void resetPasswordGetStatusOk() {
        LocalDateTime now = LocalDateTime.now();

        doReturn(Optional.of(user)).when(userRepository).findUserByEmail(user.getEmail());
        doReturn(Optional.of(now.minusDays(2))).when(userRepository).lastTimeResetPassword(user.getEmail());
        doReturn(true).when(userRepository).isEnabled(user.getEmail());
        doReturn("").when(passwordEncoder).encode(anyString());


        doNothing().when(userRepository).updateUserLastTimeResetPassword(any(), any());
        doNothing().when(emailSender).send(anyString(), anyString(), anyString());


        ResponseEntity<String> responseEntity = resetPasswordService.resetUserPassword(user.getEmail());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New password sent to your email", responseEntity.getBody());


        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
        verify(userRepository, times(1)).lastTimeResetPassword(user.getEmail());
        verify(userRepository, times(1)).lastTimeResetPassword(anyString());
        verify(userRepository, times(1)).isEnabled(user.getEmail());
        verify(userRepository, times(1)).resetUserPassword(anyString(), anyString());
        verify(userRepository, times(1)).updateUserLastTimeResetPassword(any(), any());
        verifyNoMoreInteractions(userRepository);


        verify(emailSender, times(1)).send(anyString(), anyString(), anyString());
        verifyNoMoreInteractions(emailSender);
        verify(passwordEncoder, times(1)).encode(anyString());
        verifyNoMoreInteractions(passwordEncoder);

    }

}