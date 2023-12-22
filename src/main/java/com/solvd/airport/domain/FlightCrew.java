package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class FlightCrew {
    private int flightCrewId;
    private String airlineId;
    private String flightId;


    public FlightCrew() {
    }

    public FlightCrew(int flightCrewId, String airlineId, String flightId) {
        this.flightCrewId = flightCrewId;
        this.airlineId = airlineId;
        this.flightId = flightId;
    }


    public int getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "flightCrewId",
                "airlineId",
                "flightId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    flight_crew (
        flight_crew_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        airline_id VARCHAR(2),
        flight_id VARCHAR(10),
        PRIMARY KEY(flight_crew_id)
    );
    ALTER TABLE flight_crew
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code);
*/
