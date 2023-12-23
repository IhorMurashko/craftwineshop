package com.craftWine.shop.controllers;

import com.craftWine.shop.authentication.RegistrationService;
import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import com.craftWine.shop.exceptions.EmailProblemException;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;
    private RegisterDTO registerDTO;

    @Captor
    private ArgumentCaptor<RegisterDTO> registerDTOArgumentCaptor;


    @BeforeEach
    void initRegisterDTO() {
        registerDTO = new RegisterDTO("email@gmail.com", "password", "password",
                "123456789012", "name", "last name");
    }

    @Test
    @DisplayName("/reg/registration return HTTP status CREATED and message success")
    void successRegister() {


        doReturn(ResponseEntity.status(HttpStatus.CREATED).body("success")).when(registrationService).register(registerDTO);


        ResponseEntity<String> response = registrationController.register(registerDTO);
        verify(registrationService).register(registerDTOArgumentCaptor.capture());


        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success", response.getBody());

        assertEquals(registerDTO, registerDTOArgumentCaptor.getValue());

        verify(registrationService, times(1)).register(registerDTO);
        verifyNoMoreInteractions(registrationService);

    }


    @Test
    @DisplayName("reg/registration return HTTP status CONFLICT and message The account has already been enabled")
    void conflictRegister() {

        doReturn(ResponseEntity.status(HttpStatus.CONFLICT).body("The account has already been enabled"))
                .when(registrationService).register(registerDTO);

        ResponseEntity<String> response = this.registrationController.register(registerDTO);
        verify(registrationService).register(registerDTOArgumentCaptor.capture());

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("The account has already been enabled", response.getBody());

        verify(registrationService, times(1)).register(registerDTO);
        verifyNoMoreInteractions(registrationService);

        assertEquals(registerDTO, registerDTOArgumentCaptor.getValue());

    }


    @Test
    void getExceptionWhen_UserEmailNotFound() {
        doThrow(new EmailProblemException("Could not find account with email " + registerDTO.getEmail()))
                .when(registrationService).register(registerDTO);


        RuntimeException runtimeException = assertThrows(EmailProblemException.class, () ->
                this.registrationController.register(registerDTO));
        verify(registrationService).register(registerDTOArgumentCaptor.capture());

        verify(registrationService, times(1)).register(registerDTO);
        verifyNoMoreInteractions(registrationService);
        assertEquals(registerDTO, registerDTOArgumentCaptor.getValue());


        assertEquals(EmailProblemException.class, runtimeException.getClass());
        assertEquals("Could not find account with email " + registerDTO.getEmail(), runtimeException.getMessage());


    }


    @Test
    void rememberThePassword() {
    }

    @Test
    void confirm() {
    }
}