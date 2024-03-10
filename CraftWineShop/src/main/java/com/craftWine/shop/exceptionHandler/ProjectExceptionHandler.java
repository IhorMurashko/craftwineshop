package com.craftWine.shop.exceptionHandler;

import com.craftWine.shop.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@ControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(InvalidConfirmationPasswordException.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(InvalidConfirmationPasswordException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailProblemException.class)
    public ResponseEntity<ResponseException> invalidConfirmationPasswordExceptionHandler(EmailProblemException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseException> duplicateUniqueConstraintExceptionHandler(DataIntegrityViolationException exception) {
        if (exception.getMessage().contains("duplicate key")) {

            String exceptionMessage = exception.getMessage().replaceAll("_", " ");
            String response = exceptionMessage.substring(exceptionMessage.indexOf("(") + 1, exceptionMessage.indexOf(")"));


            return new ResponseEntity<ResponseException>(new ResponseException("Duplicate: " + response + ". Please, enter new:"), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ResponseException> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseException> failIOExceptionHandler(IOException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException("Failed input-output: " + exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CraftWineNotFoundException.class)
    public ResponseEntity<ResponseException> couldNotFoundCraftWine(CraftWineNotFoundException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotFoundException.class, FavoriteEmptyListException.class})
    public ResponseEntity<ResponseException> couldNotFound(UserNotFoundException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseException> validationFormException(
            MethodArgumentNotValidException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MultipartFileException.class)
    public ResponseEntity<ResponseException> multipartFileException(MultipartFileException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseException> runtimeException(RuntimeException exception) {
        return new ResponseEntity<ResponseException>(new ResponseException(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
