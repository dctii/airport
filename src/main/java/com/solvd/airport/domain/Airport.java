package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

public class Airport {
    private String airportCode;
    private String airportName;
    private int addressId;

    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int AIRPORT_NAME_MAX_WIDTH = 100;

    public Airport() {
    }

    public Airport(String airportCode) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);
        this.airportCode = airportCode;
    }

    public Airport(String airportCode, int addressId) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportCode = airportCode;
        this.addressId = addressId;
    }

    public Airport(String airportCode, String airportName, int addressId) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        airportName, AIRPORT_NAME_MAX_WIDTH
                )
        );

        this.airportCode = airportCode;
        this.airportName = airportName;
        this.addressId = addressId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        ExceptionUtils.isStringLengthValid(airportName, AIRPORT_NAME_MAX_WIDTH);

        this.airportName = airportName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        Class<?> currClass = Airport.class;
        String[] fieldNames = {
                "airportCode",
                "airportName",
                "addressId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
