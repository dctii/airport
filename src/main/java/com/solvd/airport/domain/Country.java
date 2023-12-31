package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

public class Country {
    private String countryCode;
    private String countryName;

    final static private int COUNTRY_CODE_MAX_WIDTH = 2;
    final static private int COUNTRY_NAME_MAX_WIDTH = 75;


    public Country() {
    }

    public Country(String countryCode, String countryName) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        countryCode, COUNTRY_CODE_MAX_WIDTH,
                        countryName, COUNTRY_NAME_MAX_WIDTH
                )
        );
        ExceptionUtils.isValidCountryCode(countryCode);

        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        ExceptionUtils.isStringLengthValid(countryCode, COUNTRY_CODE_MAX_WIDTH);
        ExceptionUtils.isValidCountryCode(countryCode);

        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        ExceptionUtils.isStringLengthValid(countryName, COUNTRY_NAME_MAX_WIDTH);

        this.countryName = countryName;
    }

    @Override
    public String toString() {
        Class<?> currClass = Country.class;
        String[] fieldNames = {
                "countryCode",
                "countryName"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
