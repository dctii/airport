package com.solvd.airport.exception;

public class UnsuccessfulDeleteException extends RuntimeException {
    public UnsuccessfulDeleteException (String message) {
        super(message);
    }
}
