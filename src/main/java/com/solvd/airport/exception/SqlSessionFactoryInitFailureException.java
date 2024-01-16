package com.solvd.airport.exception;

public class SqlSessionFactoryInitFailureException extends RuntimeException {
    public SqlSessionFactoryInitFailureException(String message) {
        super(message);
    }
}
