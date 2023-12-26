package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class CheckIn {
    private int checkInId;
    private String checkInMethod;
    private boolean passIssued;
    private int airlineStaffId;
    private int bookingId;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int CHECK_IN_METHOD_MAX_WIDTH = 10;

    public CheckIn() {
    }

    public CheckIn(int checkInId, String checkInMethod, boolean passIssued, int airlineStaffId, int bookingId) {
        this.checkInId = checkInId;
        this.checkInMethod = checkInMethod;
        this.passIssued = passIssued;
        this.airlineStaffId = airlineStaffId;
        this.bookingId = bookingId;
    }

    public CheckIn(String checkInMethod, int airlineStaffId, int bookingId) {
        this.checkInMethod = checkInMethod;
        this.airlineStaffId = airlineStaffId;
        this.bookingId = bookingId;
    }

    public CheckIn(String checkInMethod, boolean passIssued, int airlineStaffId, int bookingId) {
        this.checkInMethod = checkInMethod;
        this.passIssued = passIssued;
        this.airlineStaffId = airlineStaffId;
        this.bookingId = bookingId;
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public String getCheckInMethod() {
        return checkInMethod;
    }

    public void setCheckInMethod(String checkInMethod) {
        this.checkInMethod = checkInMethod;
    }

    public boolean isPassIssued() {
        return passIssued;
    }

    public void setPassIssued(boolean passIssued) {
        this.passIssued = passIssued;
    }

    public int getAirlineStaffId() {
        return airlineStaffId;
    }

    public void setAirlineStaffId(int airlineStaffId) {
        this.airlineStaffId = airlineStaffId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        Class<?> currClass = CheckIn.class;
        String[] fieldNames = {
                "checkInId",
                "checkInMethod",
                "passIssued",
                "airlineStaffId",
                "bookingId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
