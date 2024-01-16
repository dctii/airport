package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.HashSet;
import java.util.Set;

public class EmailAddress {
    private int emailAddressId;
    private String emailAddress;

    private Set<PersonInfo> peopleWithEmailAddress;

    final static private int EMAIL_ADDRESS_MAX_WIDTH = 100;

    public EmailAddress() {
        this.peopleWithEmailAddress = new HashSet<>();
    }

    public EmailAddress(int emailAddressId, String emailAddress) {
        ExceptionUtils.isStringLengthValid(emailAddress, EMAIL_ADDRESS_MAX_WIDTH);

        this.emailAddressId = emailAddressId;
        this.emailAddress = emailAddress;
        this.peopleWithEmailAddress = new HashSet<>();
    }

    public EmailAddress(String emailAddress) {
        ExceptionUtils.isStringLengthValid(emailAddress, EMAIL_ADDRESS_MAX_WIDTH);

        this.emailAddress = emailAddress;
        this.peopleWithEmailAddress = new HashSet<>();
    }

    public int getEmailAddressId() {
        return emailAddressId;
    }

    public void setEmailAddressId(int emailAddressId) {
        this.emailAddressId = emailAddressId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        ExceptionUtils.isStringLengthValid(emailAddress, EMAIL_ADDRESS_MAX_WIDTH);

        this.emailAddress = emailAddress;
    }

    public Set<PersonInfo> getPeopleWithEmailAddress() {
        if (peopleWithEmailAddress == null) {
            peopleWithEmailAddress = new HashSet<>();
        }
        return peopleWithEmailAddress;
    }

    public void setPeopleWithEmailAddress(Set<PersonInfo> peopleWithEmailAddress) {
        this.peopleWithEmailAddress = peopleWithEmailAddress;
    }

    public void addPersonWithEmailAddress(PersonInfo personWithEmailAddress) {
        if (this.peopleWithEmailAddress == null) {
            this.peopleWithEmailAddress = new HashSet<>();
        }
        this.peopleWithEmailAddress.add(personWithEmailAddress);
    }

    public boolean removePersonWithEmailAddress(PersonInfo personWithEmailAddress) {
        return this.peopleWithEmailAddress != null
                && this.peopleWithEmailAddress.remove(personWithEmailAddress);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.EMAIL_ADDRESS;
        String[] fieldNames = {
                "emailAddressId",
                "emailAddress",
                "peopleWithEmailAddress"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
