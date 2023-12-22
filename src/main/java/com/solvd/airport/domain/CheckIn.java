package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class CheckIn {
    private int checkInId;
    private String checkInMethod;
    private boolean passIssued;
    private int airportStaffId;
    private int bookingId;

    public CheckIn() {
    }

    public CheckIn(int checkInId, String checkInMethod, boolean passIssued, int airportStaffId, int bookingId) {
        this.checkInId = checkInId;
        this.checkInMethod = checkInMethod;
        this.passIssued = passIssued;
        this.airportStaffId = airportStaffId;
        this.bookingId = bookingId;
    }

    public CheckIn(String checkInMethod, int airportStaffId, int bookingId) {
        this.checkInMethod = checkInMethod;
        this.airportStaffId = airportStaffId;
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

    public int getAirportStaffId() {
        return airportStaffId;
    }

    public void setAirportStaffId(int airportStaffId) {
        this.airportStaffId = airportStaffId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "checkInId",
                "checkInMethod",
                "passIssued",
                "airportStaffId",
                "bookingId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    check_ins (
        check_in_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        check_in_method VARCHAR(10)
            CHECK (
                check_in_method IN ('self', 'staff')
            ),
        pass_issued BOOL NOT NULL DEFAULT 0,
        airport_staff_id INT UNSIGNED NOT NULL,
        booking_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(check_in_id)
    );
    ALTER TABLE check_ins
    ADD
        FOREIGN KEY(airport_staff_id) REFERENCES airport_staff(airport_staff_id),
    ADD
        FOREIGN KEY(booking_id) REFERENCES bookings(booking_id);
*/
