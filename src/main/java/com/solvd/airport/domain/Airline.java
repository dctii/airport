package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Airline {
    private String iataAirlineCode;
    private String icaoAirlineCode;
    private String airlineName;

    public Airline() {
    }

    public Airline(String iataAirlineCode, String icaoAirlineCode, String airlineName) {
        this.iataAirlineCode = iataAirlineCode;
        this.icaoAirlineCode = icaoAirlineCode;
        this.airlineName = airlineName;
    }

    public String getIataAirlineCode() {
        return iataAirlineCode;
    }

    public void setIataAirlineCode(String iataAirlineCode) {
        this.iataAirlineCode = iataAirlineCode;
    }

    public String getIcaoAirlineCode() {
        return icaoAirlineCode;
    }

    public void setIcaoAirlineCode(String icaoAirlineCode) {
        this.icaoAirlineCode = icaoAirlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "iataAirlineCode",
                "icaoAirlineCode",
                "airlineName"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    airlines (
        IATA_airline_code VARCHAR(2) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(IATA_airline_code) = 2
                AND IATA_airline_code REGEXP '^[A-Z0-9]+$'
            ),
        ICAO_airline_code VARCHAR(3) UNIQUE
            CHECK(
                    CHAR_LENGTH(ICAO_airline_code) = 3
                    AND ICAO_airline_code REGEXP '^[A-Z]+$'
                ),
        airline_name VARCHAR(45) NOT NULL UNIQUE,
        PRIMARY KEY(IATA_airline_code)
    );
*/
