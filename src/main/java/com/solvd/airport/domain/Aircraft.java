package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Aircraft {
    private String aircraftRegistrationCode;
    private String countryId;
    private String aircraftModel;
    private short passengerCapacity; // SMALLINT in SQL
    private String flightId;


    public Aircraft() {
    }

    public Aircraft(String aircraftRegistrationCode, String countryId, String aircraftModel, short passengerCapacity, String flightId) {
        this.aircraftRegistrationCode = aircraftRegistrationCode;
        this.countryId = countryId;
        this.aircraftModel = aircraftModel;
        this.passengerCapacity = passengerCapacity;
        this.flightId = flightId;
    }

    public String getAircraftRegistrationCode() {
        return aircraftRegistrationCode;
    }

    public void setAircraftRegistrationCode(String aircraftRegistrationCode) {
        this.aircraftRegistrationCode = aircraftRegistrationCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public short getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(short passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
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
                "aircraftRegistrationCode",
                "countryId",
                "aircraftModel",
                "passengerCapacity",
                "flightId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

/*
CREATE TABLE
    aircrafts (
        aircraft_registration_code VARCHAR(45) NOT NULL,
        country_id VARCHAR(2),
        aircraft_model VARCHAR(45),
        passenger_capacity SMALLINT UNSIGNED,
        flight_id VARCHAR(10),
        PRIMARY KEY(aircraft_registration_code, country_id)
    );
    ALTER TABLE aircrafts
    ADD
        FOREIGN KEY(flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY(country_id) REFERENCES countries(ISO3166_a2_code);
*/
