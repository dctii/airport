package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

public class Address {
    private int addressId;
    private String street;
    private String citySubdivision;
    private String city;
    private String citySuperdivision;
    private String countryCode;
    private String postalCode;
    private String timezone;

    final static private int STREET_MAX_WIDTH = 45;
    final static private int CITY_SUBDIVISION_MAX_WIDTH = 45;
    final static private int CITY_MAX_WIDTH = 45;
    final static private int CITY_SUPERDIVISION_MAX_WIDTH = 45;
    final static private int COUNTRY_CODE_MAX_WIDTH = 2;
    final static private int POSTAL_CODE_MAX_WIDTH = 45;
    final static private int TIMEZONE_CODE_MAX_WIDTH = 45;


    public Address() {
    }

    public Address(int addressId, String street, String citySubdivision, String city,
                   String citySuperdivision, String countryCode, String postalCode,
                   String timezone) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        street, STREET_MAX_WIDTH,
                        citySubdivision, CITY_SUBDIVISION_MAX_WIDTH,
                        city, CITY_MAX_WIDTH,
                        citySuperdivision, CITY_SUPERDIVISION_MAX_WIDTH,
                        countryCode, COUNTRY_CODE_MAX_WIDTH,
                        postalCode, POSTAL_CODE_MAX_WIDTH,
                        timezone, TIMEZONE_CODE_MAX_WIDTH
                )
        );

        this.addressId = addressId;
        this.street = street;
        this.citySubdivision = citySubdivision;
        this.city = city;
        this.citySuperdivision = citySuperdivision;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.timezone = timezone;
    }

    public Address(String street, String city, String postalCode, String countryCode) {
        this.street = street;
        this.city = city;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        ExceptionUtils.isStringLengthValid(street, STREET_MAX_WIDTH);
        this.street = street;
    }

    public String getCitySubdivision() {
        return citySubdivision;
    }

    public void setCitySubdivision(String citySubdivision) {
        ExceptionUtils.isStringLengthValid(citySubdivision, CITY_SUBDIVISION_MAX_WIDTH);
        this.citySubdivision = citySubdivision;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        ExceptionUtils.isStringLengthValid(city, CITY_MAX_WIDTH);
        this.city = city;
    }

    public String getCitySuperdivision() {
        return citySuperdivision;
    }

    public void setCitySuperdivision(String citySuperdivision) {
        ExceptionUtils.isStringLengthValid(citySuperdivision, CITY_SUPERDIVISION_MAX_WIDTH);
        this.citySuperdivision = citySuperdivision;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountry(String countryCode) {
        ExceptionUtils.isStringLengthValid(countryCode, COUNTRY_CODE_MAX_WIDTH);
        this.countryCode = countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        ExceptionUtils.isStringLengthValid(postalCode, POSTAL_CODE_MAX_WIDTH);
        this.postalCode = postalCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        ExceptionUtils.isStringLengthValid(timezone, TIMEZONE_CODE_MAX_WIDTH);
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        Class<?> currClass = Address.class;
        String[] fieldNames = {
                "addressId",
                "street",
                "citySubdivision",
                "city",
                "citySuperdivision",
                "countryCode",
                "postalCode",
                "timezone"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
