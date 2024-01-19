package com.craftWine.shop.authentication;

import com.craftWine.shop.models.User;
import com.craftWine.shop.repositories.UserRepository;
import com.craftWine.shop.security.TokenProvider;
import com.craftWine.shop.security.token.ConfirmationToken;
import com.craftWine.shop.security.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterAndAuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    private UserRegisterAndAuthenticationService userRegisterAndAuthenticationService;


    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor

    private ArgumentCaptor<String> stringArgumentCaptor;


    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("test@email.com", "password", "012345678901",
                "name", "last name");
    }


    @Test
    @DisplayName("success sign up user")
    void successSignUp() {
        doReturn("password").when(bCryptPasswordEncoder).encode(anyString());

        doReturn(user).when(userRepository).save(any(User.class));
        doNothing().when(confirmationTokenService)
                .saveConfirmationToken(any(ConfirmationToken.class));


        String token = userRegisterAndAuthenticationService.signUpUser(user);
        verify(userRepository).save(userArgumentCaptor.capture());
        verify(bCryptPasswordEncoder).encode(stringArgumentCaptor.capture());

        assertNotNull(token);

        verify(userRepository, times(1)).save(any(User.class));
        verify(bCryptPasswordEncoder, times(1)).encode(anyString());
        verify(confirmationTokenService, times(1))
                .saveConfirmationToken(any(ConfirmationToken.class));

        verifyNoMoreInteractions(userRepository, bCryptPasswordEncoder, confirmationTokenService);


        assertEquals(user, userArgumentCaptor.getValue());
        assertEquals(user.getPassword(), stringArgumentCaptor.getValue());

    }
}