package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Address {
    private int addressId;
    private String street;
    private String citySubdivision;
    private String city;
    private String citySuperdivision;
    private String country;
    private String postalCode;
    private String timezone;
    private String countryId;


    public Address() {
    }

    public Address(int addressId, String street, String citySubdivision, String city,
                   String citySuperdivision, String country, String postalCode,
                   String timezone, String countryId) {
        this.addressId = addressId;
        this.street = street;
        this.citySubdivision = citySubdivision;
        this.city = city;
        this.citySuperdivision = citySuperdivision;
        this.country = country;
        this.postalCode = postalCode;
        this.timezone = timezone;
        this.countryId = countryId;
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
        this.street = street;
    }

    public String getCitySubdivision() {
        return citySubdivision;
    }

    public void setCitySubdivision(String citySubdivision) {
        this.citySubdivision = citySubdivision;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitySuperdivision() {
        return citySuperdivision;
    }

    public void setCitySuperdivision(String citySuperdivision) {
        this.citySuperdivision = citySuperdivision;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "addressId",
                "street",
                "citySubdivision",
                "city",
                "citySuperdivision",
                "country",
                "postalCode",
                "timezone",
                "countryId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    addresses (
        address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        street VARCHAR(45) NOT NULL,
        city_subdivision VARCHAR(45),
        city VARCHAR(45) NOT NULL,
        city_superdivision VARCHAR(45),
        country VARCHAR(75) NOT NULL,
        postal_code VARCHAR(20)
            CHECK (
                postal_code REGEXP '^[A-Z0-9]+$'
            ),
        timezone VARCHAR(45) NOT NULL,
        country_id VARCHAR(2) NOT NULL,
        PRIMARY KEY(address_id)
    );
        ALTER TABLE addresses
    ADD
        FOREIGN KEY(country_id) REFERENCES countries(ISO3166_a2_code),
    ADD
        FOREIGN KEY(country) REFERENCES countries(country_name),
    ADD
        FOREIGN KEY(timezone) REFERENCES timezones(timezone_name);
*/
