package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Airport {
    private String iataAirportCode;
    private String icaoAirportCode;
    private String airportName;
    private String timezoneName;
    private int addressId;

    public Airport() {
    }

    public Airport(String iataAirportCode, String icaoAirportCode, String airportName, String timezoneName, int addressId) {
        this.iataAirportCode = iataAirportCode;
        this.icaoAirportCode = icaoAirportCode;
        this.airportName = airportName;
        this.timezoneName = timezoneName;
        this.addressId = addressId;
    }

    public String getIataAirportCode() {
        return iataAirportCode;
    }

    public void setIataAirportCode(String iataAirportCode) {
        this.iataAirportCode = iataAirportCode;
    }

    public String getIcaoAirportCode() {
        return icaoAirportCode;
    }

    public void setIcaoAirportCode(String icaoAirportCode) {
        this.icaoAirportCode = icaoAirportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "iataAirportCode",
                "icaoAirportCode",
                "airportName",
                "timezoneName",
                "addressId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    airports (
        IATA_airport_code VARCHAR(3) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(IATA_airport_code) = 3
                AND IATA_airport_code REGEXP '^[A-Z]+$'

            ),
        ICAO_airport_code VARCHAR(4) UNIQUE
            CHECK(
                CHAR_LENGTH(ICAO_airport_code) = 4
                AND ICAO_airport_code REGEXP '^[A-Z]+$'
            ),
        airport_name VARCHAR(100) UNIQUE,
        timezone_name VARCHAR(45),
        address_id INT UNSIGNED,
        PRIMARY KEY(IATA_airport_code)
    );
    ALTER TABLE airports
    ADD
        FOREIGN KEY(address_id) REFERENCES addresses(address_id),
    ADD
        FOREIGN KEY(timezone_name) REFERENCES timezones(timezone_name);
*/
