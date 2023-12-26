package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class Airline {
    private String airlineCode;
    private String airlineName;
    private int addressId;

    final static private int AIRLINE_CODE_MAX_WIDTH = 2;
    final static private int AIRLINE_NAME_MAX_WIDTH = 45;

    public Airline() {
    }

    public Airline(String airlineCode, int addressId) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
        this.addressId = addressId;
    }

    public Airline(String airlineCode, String airlineName, int addressId) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);
        ExceptionUtils.isStringLengthValid(airlineName, AIRLINE_NAME_MAX_WIDTH);

        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
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
