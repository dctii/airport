package com.solvd.airport.persistence.dao;

import com.solvd.airport.domain.Booking;

public interface BookingDAO {
    Booking findByBookingNumber(String bookingNumber);
    void updateBooking(Booking booking);
}
