package com.solvd.airport.exception;

public class ReadJsonFailureException extends RuntimeException {
    public ReadJsonFailureException(String message) {
        super(message);
    }
}
