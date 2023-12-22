package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Country {
    private String iso3166A2Code;
    private String iso3166A3Code;
    private String countryName;


    public Country() {
    }

    public Country(String iso3166A2Code, String iso3166A3Code, String countryName) {
        this.iso3166A2Code = iso3166A2Code;
        this.iso3166A3Code = iso3166A3Code;
        this.countryName = countryName;
    }


    public String getIso3166A2Code() {
        return iso3166A2Code;
    }

    public void setIso3166A2Code(String iso3166A2Code) {
        this.iso3166A2Code = iso3166A2Code;
    }

    public String getIso3166A3Code() {
        return iso3166A3Code;
    }

    public void setIso3166A3Code(String iso3166A3Code) {
        this.iso3166A3Code = iso3166A3Code;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "iso3166A2Code",
                "iso3166A3Code",
                "countryName"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    countries (
        ISO3166_a2_code VARCHAR(2) NOT NULL UNIQUE
            CHECK(
                CHAR_LENGTH(ISO3166_a2_code) = 2
                AND ISO3166_a2_code REGEXP '^[A-Z]+$'
            ),
        ISO3166_a3_code VARCHAR(3) UNIQUE
            CHECK(
                CHAR_LENGTH(ISO3166_a3_code) = 3
                AND ISO3166_a3_code REGEXP '^[A-Z]+$'
            ),
        country_name VARCHAR(75) NOT NULL UNIQUE,
        PRIMARY KEY(ISO3166_a2_code)
    );
*/
