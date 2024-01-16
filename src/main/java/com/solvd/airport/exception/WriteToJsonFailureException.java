package com.solvd.airport.exception;

public class WriteToJsonFailureException extends RuntimeException {
    public WriteToJsonFailureException(String message) {
        super(message);
    }
}
