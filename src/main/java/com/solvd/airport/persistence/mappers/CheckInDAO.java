package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.CheckIn;

public interface CheckInDAO {
    void createCheckIn(CheckIn checkIn);
    CheckIn getCheckInById(int id);
    CheckIn getCheckInByBookingNumber(String bookingNumber);
    void updateCheckIn(CheckIn checkIn);
    void deleteCheckIn(int id);

    boolean hasCheckInForBookingId(int bookingId);
}
