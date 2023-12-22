package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private String bookingNumber;
    private Timestamp purchaseDatetime;
    private String seatClass;
    private String seatNumber;
    private String bookingStatus;
    private BigDecimal price;
    private int passengerId;
    private String flightId;
    private int bookingAgencyId;


    public Booking() {
    }

    public Booking(int bookingId, String bookingNumber, Timestamp purchaseDatetime,
                   String seatClass, String seatNumber, String bookingStatus,
                   BigDecimal price, int passengerId, String flightId, int bookingAgencyId) {
        this.bookingId = bookingId;
        this.bookingNumber = bookingNumber;
        this.purchaseDatetime = purchaseDatetime;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.bookingStatus = bookingStatus;
        this.price = price;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.bookingAgencyId = bookingAgencyId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Timestamp getPurchaseDatetime() {
        return purchaseDatetime;
    }

    public void setPurchaseDatetime(Timestamp purchaseDatetime) {
        this.purchaseDatetime = purchaseDatetime;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public int getBookingAgencyId() {
        return bookingAgencyId;
    }

    public void setBookingAgencyId(int bookingAgencyId) {
        this.bookingAgencyId = bookingAgencyId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "bookingId",
                "bookingNumber",
                "purchaseDatetime",
                "seatClass",
                "seatNumber",
                "bookingStatus",
                "price",
                "passengerId",
                "flightId",
                "bookingAgencyId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }

}


/*
CREATE TABLE
    bookings (
        booking_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        booking_number VARCHAR(45),
        purchase_datetime DATETIME NOT NULL,
        seat_class VARCHAR(45) NOT NULL
            CHECK (
                seat_class IN (
                    'economy',
                    'premium',
                    'business',
                    'first')
                ),
        seat_number VARCHAR(10),
        booking_status VARCHAR(45) NOT NULL
            CHECK (
                booking_status IN (
                    'confirmed',
                    'checked-in',
                    'boarded',
                    'no show',
                    'cancelled',
                    'pending',
                    'waitlisted'
                )
            ),
        price DECIMAL(10, 2)
            CHECK (price >= 0.00),
        passenger_id INT UNSIGNED NOT NULL,
        flight_id VARCHAR(10),
        booking_agency_id INT UNSIGNED,
        PRIMARY KEY(booking_id)
    );
    ALTER TABLE bookings
    ADD
        FOREIGN KEY(passenger_id) REFERENCES passengers(passenger_id),
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY(booking_agency_id) REFERENCES booking_agencies(booking_agency_id);
*/
