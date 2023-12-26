package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class PersonContact {
    private int personInfoId;

    public PersonContact() {
    }

    public PersonContact(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = PersonContact.class;
        String[] fieldNames = {
                "personInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
