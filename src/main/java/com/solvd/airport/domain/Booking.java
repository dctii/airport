package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

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
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        bookingNumber, BOOKING_NUMBER_MAX_WIDTH,
                        seatClass, SEAT_CLASS_MAX_WIDTH,
                        status, STATUS_MAX_WIDTH,
                        agency, AGENCY_MAX_WIDTH,
                        passportNumber, PASSPORT_NUMBER_MAX_WIDTH,
                        flightCode, FLIGHT_CODE_MAX_WIDTH
                ));
        ExceptionUtils.isDecimalValid(price, PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE);

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
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        bookingNumber, BOOKING_NUMBER_MAX_WIDTH,
                        seatClass, SEAT_CLASS_MAX_WIDTH,
                        seatNumber, SEAT_NUMBER_MAX_WIDTH,
                        status, STATUS_MAX_WIDTH,
                        agency, AGENCY_MAX_WIDTH,
                        passportNumber, PASSPORT_NUMBER_MAX_WIDTH,
                        flightCode, FLIGHT_CODE_MAX_WIDTH
                ));
        ExceptionUtils.isDecimalValid(price, PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE);

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
        ExceptionUtils.isStringLengthValid(bookingNumber, BOOKING_NUMBER_MAX_WIDTH);

        this.bookingNumber = bookingNumber;
    }

    public Timestamp getPurchaseDatetime() {
        return purchaseDatetime;
    }

    public void setPurchaseDatetime(Timestamp purchaseDatetime) {
        this.purchaseDatetime = purchaseDatetime;
    }

    public void setPurchaseDatetime(String purchaseDatetime) {
        ExceptionUtils.isValidTimestamp(
                purchaseDatetime,
                StringConstants.TIMESTAMP_PATTERN
        );

        this.purchaseDatetime = SQLUtils.toTimestamp(purchaseDatetime);
    }



    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        ExceptionUtils.isStringLengthValid(seatClass, SEAT_CLASS_MAX_WIDTH);
        this.seatClass = seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        ExceptionUtils.isStringLengthValid(seatNumber, SEAT_NUMBER_MAX_WIDTH);
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        ExceptionUtils.isStringLengthValid(status, STATUS_MAX_WIDTH);
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        ExceptionUtils.isDecimalValid(price, PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE);
        this.price = price;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        ExceptionUtils.isStringLengthValid(agency, AGENCY_MAX_WIDTH);
        this.agency = agency;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);
        this.passportNumber = passportNumber;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        ExceptionUtils.isStringLengthValid(flightCode, FLIGHT_CODE_MAX_WIDTH);
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
