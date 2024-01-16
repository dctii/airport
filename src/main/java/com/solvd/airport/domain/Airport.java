package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Airport {
    private int airportId;
    private String airportCode;
    private String airportName;
    private Integer addressId;
    private Set<Terminal> terminals;

    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int AIRPORT_NAME_MAX_WIDTH = 100;

    public Airport() {
    }

    public Airport(String airportCode) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);
        this.airportCode = airportCode;
    }

    public Airport(int airportId, String airportCode) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportId = airportId;
        this.airportCode = airportCode;
    }

    public Airport(String airportCode, String airportName) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        airportName, AIRPORT_NAME_MAX_WIDTH
                )
        );

        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public Airport(int airportId, String airportCode, String airportName) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        airportName, AIRPORT_NAME_MAX_WIDTH
                )
        );

        this.airportId = airportId;
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public Airport(String airportCode, int addressId) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportCode = airportCode;
        this.addressId = addressId;
    }

    public Airport(int airportId, String airportCode, int addressId) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportId = airportId;
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

    public Airport(int airportId, String airportCode, String airportName, int addressId) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        airportName, AIRPORT_NAME_MAX_WIDTH
                )
        );

        this.airportId = airportId;
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.addressId = addressId;
    }

    public Integer getAirportId() {
        return airportId;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Set<Terminal> getTerminals() {
        if (terminals == null) {
            terminals = new HashSet<>();
        }
        return terminals;
    }

    public void setTerminals(Set<Terminal> terminals) {
        this.terminals = terminals;
    }

    public void addTerminal(Terminal terminal) {
        if (this.terminals == null) {
            this.terminals = new HashSet<>();
        }
        this.terminals.add(terminal);
    }

    public boolean removeTerminal(Terminal terminal) {
        return this.terminals != null
                && this.terminals.remove(terminal);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.AIRPORT;
        String[] fieldNames = {
                "airportId",
                "airportCode",
                "airportName",
                "addressId",
                "terminals"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
