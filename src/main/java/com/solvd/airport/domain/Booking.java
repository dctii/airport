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
    private String status;
    private BigDecimal price;
    private String agency;
    private String passportNumber;
    private String flightCode;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int BOOKING_NUMBER_MAX_WIDTH = 45;
    final static private int SEAT_CLASS_MAX_WIDTH = 45;
    final static private int SEAT_NUMBER_MAX_WIDTH = 10;
    final static private int STATUS_MAX_WIDTH = 45;
    final static private int PRICE_DECIMAL_PRECISION = 10;
    final static private int PRICE_DECIMAL_SCALE = 2;
    final static private int AGENCY_MAX_WIDTH = 45;
    final static private int PASSPORT_NUMBER_MAX_WIDTH = 45;
    final static private int FLIGHT_CODE_MAX_WIDTH = 10;


    public Booking() {
    }

    public Booking(int bookingId, String bookingNumber,
                   Timestamp purchaseDatetime, String seatClass, String status,
                   BigDecimal price, String agency, String passportNumber, String flightCode
    ) {
        this.bookingId = bookingId;
        this.bookingNumber = bookingNumber;
        this.purchaseDatetime = purchaseDatetime;
        this.seatClass = seatClass;
        this.status = status;
        this.price = price;
        this.agency = agency;
        this.passportNumber = passportNumber;
        this.flightCode = flightCode;
    }

    public Booking(int bookingId, String bookingNumber, Timestamp purchaseDatetime, String seatClass,
                   String seatNumber, String status, BigDecimal price, String agency, String passportNumber, String flightCode
    ) {
        this.bookingId = bookingId;
        this.bookingNumber = bookingNumber;
        this.purchaseDatetime = purchaseDatetime;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.status = status;
        this.price = price;
        this.agency = agency;
        this.passportNumber = passportNumber;
        this.flightCode = flightCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    @Override
    public String toString() {
        Class<?> currClass = Booking.class;
        String[] fieldNames = {
                "bookingId",
                "bookingNumber",
                "purchaseDatetime",
                "seatClass",
                "seatNumber",
                "status",
                "price",
                "agency",
                "passportNumber",
                "flightCode"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }

}
