package com.solvd.airport.exception;

public class NoBoardingPassFoundException extends RuntimeException {
    public NoBoardingPassFoundException(String message) {
        super(message);
    }
}
