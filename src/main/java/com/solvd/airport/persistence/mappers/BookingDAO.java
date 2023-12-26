package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.Booking;

public interface BookingDAO {
    void createBooking(Booking booking);
    Booking getBookingById(int id);
    Booking findByBookingNumber(String bookingNumber);
    void updateBooking(Booking booking);
    void deleteBooking(int id);

}
