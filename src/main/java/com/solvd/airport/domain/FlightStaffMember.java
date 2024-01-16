package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.StringFormatters;

public class FlightStaffMember {
    private int flightStaffId;
    private Integer flightCrewId;
    private Integer airlineStaffId;

    public FlightStaffMember() {
    }

    public FlightStaffMember(int flightStaffId, int airlineStaffId) {
        this.flightStaffId = flightStaffId;
        this.airlineStaffId = airlineStaffId;
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

    public Integer getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    public Integer getAirlineStaffId() {
        return airlineStaffId;
    }

    public void setAirlineStaffId(int airlineStaffId) {
        this.airlineStaffId = airlineStaffId;
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.FLIGHT_STAFF_MEMBER;
        String[] fieldNames = {
                "flightStaffId",
                "flightCrewId",
                "airlineStaffId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

