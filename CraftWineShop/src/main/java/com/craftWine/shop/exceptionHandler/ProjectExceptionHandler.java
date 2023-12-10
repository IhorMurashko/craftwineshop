package com.craftWine.shop.exceptionHandler;

import com.craftWine.shop.exceptions.CraftWineNotFoundException;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.InvalidConfirmationPasswordException;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(InvalidConfirmationPasswordException.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(@NotNull InvalidConfirmationPasswordException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailProblemException.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(@NotNull EmailProblemException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseException> duplicateUniqueConstraintExceptionHandler(@NotNull DataIntegrityViolationException exception) {
        if (exception.getMessage().contains("duplicate key")) {
            return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseException> illegalArgumentExceptionHandler(@NotNull IllegalArgumentException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseException> failIOExceptionHandler(@NotNull IOException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException("Failed input-output: " + exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CraftWineNotFoundException.class)
    public ResponseEntity<ResponseException> couldNotFoundCraftWine(@NotNull CraftWineNotFoundException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException( exception.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
