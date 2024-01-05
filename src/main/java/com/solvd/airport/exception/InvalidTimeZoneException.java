package com.solvd.airport.exception;

public class InvalidTimeZoneException extends RuntimeException{
    public InvalidTimeZoneException(String message) {
        super(message);
    }
}
