package com.solvd.airport.exception;

public class NoBookingException extends RuntimeException {
    public NoBookingException(String message) {
        super(message);
    }
}
