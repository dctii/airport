package com.solvd.airport.service;

import com.solvd.airport.exception.InvalidBookingStatusException;

public interface BoardPassengerService {
    void boardPassenger(String bookingNumber) throws InvalidBookingStatusException;
}
