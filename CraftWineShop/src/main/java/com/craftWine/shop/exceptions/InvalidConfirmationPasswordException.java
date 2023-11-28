package com.craftWine.shop.exceptions;

public class InvalidConfirmationPasswordException extends RuntimeException {
    public InvalidConfirmationPasswordException(String message) {
        super(message);
    }
}
