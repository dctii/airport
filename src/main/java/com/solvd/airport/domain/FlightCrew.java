package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class FlightCrew {
    private int flightCrewId;
    private String flightCode;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int FLIGHT_CODE_MAX_WIDTH = 10;


    public FlightCrew() {
    }

    public FlightCrew(int flightCrewId, String flightCode) {
        this.flightCrewId = flightCrewId;
        this.flightCode = flightCode;
    }

    public int getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    @Override
    public String toString() {
        Class<?> currClass = FlightCrew.class;
        String[] fieldNames = {
                "flightCrewId",
                "flightCode"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
