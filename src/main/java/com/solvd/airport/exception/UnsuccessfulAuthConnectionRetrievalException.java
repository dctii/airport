package com.solvd.airport.exception;

public class UnsuccessfulAuthConnectionRetrieval extends RuntimeException {
    public UnsuccessfulAuthConnectionRetrieval(String message) {
        super(message);
    }
}
