package com.solvd.airport.exception;

public class InvalidPhoneNumberException extends RuntimeException{
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
