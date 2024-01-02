package com.solvd.airport.exception;

public class NoCheckInException extends RuntimeException {
    public NoCheckInException(String message) {
        super(message);
    }
}
