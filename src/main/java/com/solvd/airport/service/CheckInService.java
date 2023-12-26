package com.solvd.airport.service;

public interface CheckInService {
    // check passport holder in by bookingNumber and flightId
    void performCheckIn(String staffEmail, String bookingNumber, boolean hasBaggage, double baggageWeight);
}
