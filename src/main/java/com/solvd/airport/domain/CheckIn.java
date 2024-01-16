package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class CheckIn {

    private int checkInId;
    private String checkInMethod;
    private boolean passIssued;
    private Integer airlineStaffId;
    private Integer bookingId;

    final static private int CHECK_IN_METHOD_MAX_WIDTH = 10;

    public CheckIn() {
    }

    public CheckIn(int checkInId, String checkInMethod, boolean passIssued, int airlineStaffId, int bookingId) {
        ExceptionUtils.isStringLengthValid(checkInMethod, CHECK_IN_METHOD_MAX_WIDTH);

        this.checkInId = checkInId;
        this.checkInMethod = checkInMethod;
        this.passIssued = passIssued;
        this.airlineStaffId = airlineStaffId;
        this.bookingId = bookingId;
    }

    public CheckIn(String checkInMethod, int airlineStaffId, int bookingId) {
        ExceptionUtils.isStringLengthValid(checkInMethod, CHECK_IN_METHOD_MAX_WIDTH);

        this.checkInMethod = checkInMethod;
        this.airlineStaffId = airlineStaffId;
        this.bookingId = bookingId;
    }

    public CheckIn(String checkInMethod, boolean passIssued, int airlineStaffId, int bookingId) {
        ExceptionUtils.isStringLengthValid(checkInMethod, CHECK_IN_METHOD_MAX_WIDTH);

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
        ExceptionUtils.isStringLengthValid(checkInMethod, CHECK_IN_METHOD_MAX_WIDTH);

        this.checkInMethod = checkInMethod;
    }

    public boolean isPassIssued() {
        return passIssued;
    }

    public void setPassIssued(boolean passIssued) {
        this.passIssued = passIssued;
    }

    public Integer getAirlineStaffId() {
        return airlineStaffId;
    }

    public void setAirlineStaffId(int airlineStaffId) {
        this.airlineStaffId = airlineStaffId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.CHECK_IN;
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
