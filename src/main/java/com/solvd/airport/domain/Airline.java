package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

// TODO: Use Jackson here

public class Airline {
    private String airlineCode;
    private String airlineName;
    private int addressId;

    final static private int AIRLINE_CODE_MAX_WIDTH = 2;
    final static private int AIRLINE_NAME_MAX_WIDTH = 45;

    public Airline() {
    }

    public Airline(String airlineCode) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
    }

    public Airline(String airlineCode, String airlineName) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        airlineName, AIRLINE_NAME_MAX_WIDTH
                ));

        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
    }

    public Airline(String airlineCode, int addressId) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
        this.addressId = addressId;
    }

    public Airline(String airlineCode, String airlineName, int addressId) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        airlineName, AIRLINE_NAME_MAX_WIDTH
                ));

        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.addressId = addressId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        ExceptionUtils.isStringLengthValid(airlineName, AIRLINE_NAME_MAX_WIDTH);

        this.airlineName = airlineName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        Class<?> currClass = Airline.class;
        String[] fieldNames = {
                "airlineCode",
                "airlineName",
                "addressId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
