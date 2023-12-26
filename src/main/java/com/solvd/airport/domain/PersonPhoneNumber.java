package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class PersonPhoneNumber extends PersonContact {
    private int phoneNumberId;

    public PersonPhoneNumber(int phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public PersonPhoneNumber(int personInfoId, int phoneNumberId) {
        super(personInfoId);
        this.phoneNumberId = phoneNumberId;
    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(int phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    @Override
    public String toString() {
        Class<?> currClass = PersonPhoneNumber.class;
        String[] fieldNames = {
                "phoneNumberId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
