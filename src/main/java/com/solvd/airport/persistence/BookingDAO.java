package com.solvd.airport.persistence;

import com.solvd.airport.domain.Booking;
import org.apache.ibatis.annotations.Param;

public interface BookingDAO {
    void createBooking(@Param("bookingObj") Booking bookingObj);
    Booking getBookingById(@Param("bookingId") int bookingId);
    Booking findByBookingNumber(@Param("bookingNumber") String bookingNumber);
    void updateBooking(@Param("bookingObj") Booking bookingObj);
    void deleteBooking(@Param("bookingId") int bookingId);

//   TODO:  boolean doesBookingNumberExist(@Param("bookingNumber") String bookingNumber);

}
