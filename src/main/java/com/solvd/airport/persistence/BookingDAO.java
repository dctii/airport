package com.solvd.airport.persistence;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface BookingDAO extends AbstractDAO<Booking> {
    int create(@Param("bookingObj") Booking bookingObj);

    Booking getById(@Param("bookingId") int bookingId);

    Booking getBookingByBookingNumber(@Param("bookingNumber") String bookingNumber);

    void update(@Param("bookingObj") Booking bookingObj);

    void updateBookingByBookingNumber(@Param("bookingObj") Booking bookingObj);

    void delete(@Param("bookingId") int bookingId);
    void deleteBookingByBookingNumber(@Param("bookingNumber") String bookingNumber);

   boolean doesBookingExist(@Param("bookingNumber") String bookingNumber);


    String TABLE_NAME = "bookings";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_BOOKING_ID = "booking_id";
    String COL_BOOKING_NUMBER = "booking_number";
    String COL_PURCHASE_DATETIME = "purchase_datetime";
    String COL_SEAT_CLASS = "seat_class";
    String COL_SEAT_NUMBER = "seat_number";
    String COL_STATUS = "status";
    String COL_PRICE = "price";
    String COL_AGENCY = "agency";
    String COL_PASSPORT_NUMBER = PassportDAO.COL_PASSPORT_NUMBER;
    String COL_FLIGHT_CODE = FlightDAO.COL_FLIGHT_CODE;
    String EXPLICIT_COL_BOOKING_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOOKING_ID);
    String EXPLICIT_COL_BOOKING_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOOKING_NUMBER);
    String EXPLICIT_COL_PURCHASE_DATETIME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PURCHASE_DATETIME);
    String EXPLICIT_COL_SEAT_CLASS = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SEAT_CLASS);
    String EXPLICIT_COL_SEAT_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SEAT_NUMBER);
    String EXPLICIT_COL_STATUS = SQLUtils.qualifyColumnName(TABLE_NAME, COL_STATUS);
    String EXPLICIT_COL_PRICE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PRICE);
    String EXPLICIT_COL_AGENCY = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AGENCY);
    String EXPLICIT_COL_PASSPORT_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PASSPORT_NUMBER);
    String EXPLICIT_COL_FLIGHT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CODE);

    String STATUS_CONFIRMED = "Confirmed";
    String STATUS_CHECKED_IN = "Checked-in";
    String STATUS_BOARDED = "Boarded";

}
