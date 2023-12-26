package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class FlightStaffMember {
    private int flightStaffId;
    private int flightCrewId;
    private int airlineStaffId;

    public FlightStaffMember() {
    }

    public FlightStaffMember(int flightStaffId, int flightCrewId, int airlineStaffId) {
        this.flightStaffId = flightStaffId;
        this.flightCrewId = flightCrewId;
        this.airlineStaffId = airlineStaffId;
    }

    public int getFlightStaffId() {
        return flightStaffId;
    }

    public void setFlightStaffId(int flightStaffId) {
        this.flightStaffId = flightStaffId;
    }

    public int getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    public int getAirlineStaffId() {
        return airlineStaffId;
    }

    public void setAirlineStaffId(int airlineStaffId) {
        this.airlineStaffId = airlineStaffId;
    }

    @Override
    public String toString() {
        Class<?> currClass = FlightStaffMember.class;
        String[] fieldNames = {
                "flightStaffId",
                "flightCrewId",
                "airlineStaffId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

