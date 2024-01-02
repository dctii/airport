package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class FlightCrew {
    private int flightCrewId;
    private String flightCode;

    final static private int FLIGHT_CODE_MAX_WIDTH = 10;


    public FlightCrew() {
    }

    public FlightCrew(int flightCrewId, String flightCode) {
        ExceptionUtils.isStringLengthValid(flightCode, FLIGHT_CODE_MAX_WIDTH);

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
        ExceptionUtils.isStringLengthValid(flightCode, FLIGHT_CODE_MAX_WIDTH);

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
