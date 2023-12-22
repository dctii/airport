package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.sql.Timestamp;

public class Flight {
    private String flightCode;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String airlineId;
    private int departureGateId;
    private int arrivalGateId;
    private int flightCrewId;
    private String aircraftRegistrationId;
    private String aircraftCountryId;

    public Flight() {
    }

    public Flight(String flightCode, Timestamp departureTime, Timestamp arrivalTime,
                  String airlineId, int departureGateId, int arrivalGateId,
                  int flightCrewId, String aircraftRegistrationId, String aircraftCountryId) {
        this.flightCode = flightCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineId = airlineId;
        this.departureGateId = departureGateId;
        this.arrivalGateId = arrivalGateId;
        this.flightCrewId = flightCrewId;
        this.aircraftRegistrationId = aircraftRegistrationId;
        this.aircraftCountryId = aircraftCountryId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public int getDepartureGateId() {
        return departureGateId;
    }

    public void setDepartureGateId(int departureGateId) {
        this.departureGateId = departureGateId;
    }

    public int getArrivalGateId() {
        return arrivalGateId;
    }

    public void setArrivalGateId(int arrivalGateId) {
        this.arrivalGateId = arrivalGateId;
    }

    public int getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    public String getAircraftRegistrationId() {
        return aircraftRegistrationId;
    }

    public void setAircraftRegistrationId(String aircraftRegistrationId) {
        this.aircraftRegistrationId = aircraftRegistrationId;
    }

    public String getAircraftCountryId() {
        return aircraftCountryId;
    }

    public void setAircraftCountryId(String aircraftCountryId) {
        this.aircraftCountryId = aircraftCountryId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "flightCode",
                "departureTime",
                "arrivalTime",
                "airlineId",
                "departureGateId",
                "arrivalGateId",
                "flightCrewId",
                "aircraftRegistrationId",
                "aircraftCountryId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    flights (
        flight_code VARCHAR(10) NOT NULL,
        departure_time DATETIME,
        arrival_time DATETIME,
        airline_id VARCHAR(2),
        departure_gate_id INT UNSIGNED,
        arrival_gate_id INT UNSIGNED,
        flight_crew_id INT UNSIGNED,
        aircraft_registration_id VARCHAR(45),
        aircraft_country_id VARCHAR(2),
        PRIMARY KEY(flight_code)
    );
    ALTER TABLE flights
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(arrival_gate_id) REFERENCES gates(gate_id),
    ADD
        FOREIGN KEY(departure_gate_id) REFERENCES gates(gate_id),
    ADD
        FOREIGN KEY(flight_crew_id) REFERENCES flight_crew(flight_crew_id),
    ADD
        FOREIGN KEY(aircraft_registration_id, aircraft_country_id)
            REFERENCES aircrafts(aircraft_registration_code, country_id);
*/
