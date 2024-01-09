package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

public class PhoneNumber {
    private int phoneNumberId;
    private String phoneNumber;
    private String extension;

    final static private int PHONE_NUMBER_MAX_WIDTH = 50;
    final static private int EXTENSION_MAX_WIDTH = 45;

    public PhoneNumber() {
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
    }

    public PhoneNumber(String phoneNumber) {
        ExceptionUtils.isStringLengthValid(phoneNumber, PHONE_NUMBER_MAX_WIDTH);


        this.phoneNumber = ExceptionUtils.sanitizeAndCheckPhoneNumberString(phoneNumber);
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
