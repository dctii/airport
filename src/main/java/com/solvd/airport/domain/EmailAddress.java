package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class EmailAddress {
   private int emailAddressId;
   private String emailAddress;

   final static private int EMAIL_ADDRESS_MAX_WIDTH = 100;

    public EmailAddress() {
    }

    public EmailAddress(int emailAddressId, String emailAddress) {
        ExceptionUtils.isStringLengthValid(emailAddress, EMAIL_ADDRESS_MAX_WIDTH);

        this.emailAddressId = emailAddressId;
        this.emailAddress = emailAddress;
    }

    public EmailAddress(String emailAddress) {
        ExceptionUtils.isStringLengthValid(emailAddress, EMAIL_ADDRESS_MAX_WIDTH);

        this.emailAddress = emailAddress;
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

    @Override
    public String toString() {
        Class<?> currClass = EmailAddress.class;
        String[] fieldNames = {
                "emailAddressId",
                "emailAddress"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
