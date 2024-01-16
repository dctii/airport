package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneNumber {
    private int phoneNumberId;
    private String phoneNumber;
    private String extension;

    private Set<PersonInfo> peopleWithPhoneNumber;

    final static private int PHONE_NUMBER_MAX_WIDTH = 50;
    final static private int EXTENSION_MAX_WIDTH = 45;

    public PhoneNumber() {
        this.peopleWithPhoneNumber = new HashSet<>();
    }

    public PhoneNumber(int phoneNumberId, String phoneNumber, String extension) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        phoneNumber, PHONE_NUMBER_MAX_WIDTH,
                        extension, EXTENSION_MAX_WIDTH
                )
        );
        ExceptionUtils.isValidPhoneNumberExtension(extension);


        this.phoneNumberId = phoneNumberId;
        this.phoneNumber = ExceptionUtils.sanitizeAndCheckPhoneNumberString(phoneNumber);
        this.extension = extension.toUpperCase();
        this.peopleWithPhoneNumber = new HashSet<>();
    }

    public PhoneNumber(String phoneNumber) {
        ExceptionUtils.isStringLengthValid(phoneNumber, PHONE_NUMBER_MAX_WIDTH);


        this.phoneNumber = ExceptionUtils.sanitizeAndCheckPhoneNumberString(phoneNumber);
        this.peopleWithPhoneNumber = new HashSet<>();
    }

    public int getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(int phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        ExceptionUtils.isStringLengthValid(phoneNumber, PHONE_NUMBER_MAX_WIDTH);

        this.phoneNumber = ExceptionUtils.sanitizeAndCheckPhoneNumberString(phoneNumber);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        ExceptionUtils.isStringLengthValid(extension, EXTENSION_MAX_WIDTH);
        ExceptionUtils.isValidPhoneNumberExtension(extension);

        this.extension = extension.toUpperCase();
    }

    public Set<PersonInfo> getPeopleWithPhoneNumber() {
        if (peopleWithPhoneNumber == null) {
            peopleWithPhoneNumber = new HashSet<>();
        }
        return peopleWithPhoneNumber;
    }

    public void setPeopleWithPhoneNumber(Set<PersonInfo> peopleWithPhoneNumber) {
        this.peopleWithPhoneNumber = peopleWithPhoneNumber;
    }

    public void addPersonWithPhoneNumber(PersonInfo personWithPhoneNumber) {
        if (this.peopleWithPhoneNumber == null) {
            this.peopleWithPhoneNumber = new HashSet<>();
        }
        this.peopleWithPhoneNumber.add(personWithPhoneNumber);
    }

    public boolean removePersonWithPhoneNumber(PersonInfo personWithPhoneNumber) {
        return this.peopleWithPhoneNumber != null
                && this.peopleWithPhoneNumber.remove(personWithPhoneNumber);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.PHONE_NUMBER;
        String[] fieldNames = {
                "phoneNumberId",
                "phoneNumber",
                "extension",
                "peopleWithPhoneNumber"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
