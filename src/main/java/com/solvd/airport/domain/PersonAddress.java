package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class PersonAddress extends PersonContact {

    private int addressId;

    public PersonAddress(int addressId) {
        this.addressId = addressId;
    }

    public PersonAddress(int personInfoId, int addressId) {
        super(personInfoId);
        this.addressId = addressId;
    }

    public PersonAddress() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        Class<?> currClass = PersonAddress.class;
        String[] fieldNames = {
                "addressId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
