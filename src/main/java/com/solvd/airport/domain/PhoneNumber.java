package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class PhoneNumber {
    private int phoneNumberId;
    private String phoneNumber;
    private String extension;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int PHONE_NUMBER_MAX_WIDTH = 50;
    final static private int EXTENSION_MAX_WIDTH = 45;

    public PhoneNumber() {
    }

    public PhoneNumber(int phoneNumberId, String phoneNumber, String extension) {
        this.phoneNumberId = phoneNumberId;
        this.phoneNumber = phoneNumber;
        this.extension = extension;
    }

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        this.phoneNumber = phoneNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        Class<?> currClass = PhoneNumber.class;
        String[] fieldNames = {
                "phoneNumberId",
                "phoneNumber",
                "extension"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
