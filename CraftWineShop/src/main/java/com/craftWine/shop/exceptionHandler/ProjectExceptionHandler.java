package com.craftWine.shop.exceptionHandler;

import com.craftWine.shop.exceptions.EmailProblem;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidConfirmationPasswordException.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(@NotNull InvalidConfirmationPasswordException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailProblem.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(@NotNull EmailProblem exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseException> 

}
