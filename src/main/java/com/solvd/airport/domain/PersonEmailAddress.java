package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class PersonEmailAddress extends PersonContact {
    private int emailAddressId;

    public PersonEmailAddress(int emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    public PersonEmailAddress(int personInfoId, int emailAddressId) {
        super(personInfoId);
        this.emailAddressId = emailAddressId;
    }

    public int getEmailAddressId() {
        return emailAddressId;
    }

    public void setEmailAddressId(int emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    @Override
    public String toString() {
        Class<?> currClass = PersonEmailAddress.class;
        String[] fieldNames = {
                "emailAddressId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
