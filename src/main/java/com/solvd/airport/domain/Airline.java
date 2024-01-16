package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

// Jackson

public class Airline {
    private int airlineId;
    private String airlineCode;
    private String airlineName;
    private Address address;

    final static private int AIRLINE_CODE_MAX_WIDTH = 2;
    final static private int AIRLINE_NAME_MAX_WIDTH = 45;

    public Airline() {
    }

    public Airline(String airlineCode) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
    }

    public Airline(int airlineId, String airlineCode) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineId = airlineId;
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

    public Airline(int airlineId, String airlineCode, String airlineName) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        airlineName, AIRLINE_NAME_MAX_WIDTH
                ));

        this.airlineId = airlineId;
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
    }

    public Airline(String airlineCode, Address address) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
        this.address = address;
    }

    public Airline(int airlineId, String airlineCode, Address address) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineId = airlineId;
        this.airlineCode = airlineCode;
        this.address = address;
    }

    public Airline(String airlineCode, String airlineName, Address address) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        airlineName, AIRLINE_NAME_MAX_WIDTH
                ));

        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.address = address;
    }

    public Airline(int airlineId, String airlineCode, String airlineName, Address address) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        airlineName, AIRLINE_NAME_MAX_WIDTH
                ));

        this.airlineId = airlineId;
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.address = address;
    }

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
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

    public Address getAddress() {
        return address;
    }


    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAddressId() {
        if (this.address != null) {
            return this.address.getAddressId();
        } else {
            return null;
        }
    }


    public void setAddressId(int addressId) {
        if (this.address == null) {
            this.address = new Address();
        }
        this.address.setAddressId(addressId);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.AIRLINE;
        String[] fieldNames = {
                "airlineId",
                "airlineCode",
                "airlineName",
                "address"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
