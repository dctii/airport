package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Passenger {
    private int passengerId;
    private String passportId;
    private int governmentId;
    private int bookingId;

    public Passenger() {
    }

    public Passenger(int passengerId, String passportId, int governmentId, int bookingId) {
        this.passengerId = passengerId;
        this.passportId = passportId;
        this.governmentId = governmentId;
        this.bookingId = bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public int getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(int governmentId) {
        this.governmentId = governmentId;
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
                "passengerId",
                "passportId",
                "governmentId",
                "bookingId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    passengers (
        passenger_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        passport_id VARCHAR(45),
        government_id INT UNSIGNED,
        booking_id INT UNSIGNED,
        PRIMARY KEY(passenger_id)
    );
    ALTER TABLE passengers
    ADD
        FOREIGN KEY(passport_id) REFERENCES passports(passport_number),
    ADD
        FOREIGN KEY(government_id) REFERENCES government_ids(gov_id),
    ADD
        FOREIGN KEY(booking_id) REFERENCES bookings(booking_id);
*/
