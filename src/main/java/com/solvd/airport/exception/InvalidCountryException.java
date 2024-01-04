package com.solvd.airport.exception;

public class InvalidCountryException extends RuntimeException{
    public InvalidCountryException(String message) {
        super(message);
    }
}
