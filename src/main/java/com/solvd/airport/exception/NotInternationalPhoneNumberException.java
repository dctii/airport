package com.solvd.airport.exception;

public class NotInternationalPhoneNumberException extends RuntimeException {
    public NotInternationalPhoneNumberException(String message) {
        super(message);
    }
}
