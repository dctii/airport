package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.sql.Date;
import java.util.Map;

public class Passport {
    private String passportNumber;
    private Date issueDate;
    private Date expiryDate;
    private int personInfoId;
    final static private int PASSPORT_NUMBER_MAX_WIDTH = 45;

    public Passport() {
    }

    public Passport(String passportNumber, Date issueDate, Date expiryDate, int personInfoId) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportNumber = passportNumber.toUpperCase();
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.personInfoId = personInfoId;
    }

    public Passport(String passportNumber, String issueDate, String expiryDate, int personInfoId) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);
        Map<String, String> datesToValidate = Map.of(
                issueDate, StringConstants.YEAR_FIRST_DATE_PATTERN,
                expiryDate, StringConstants.YEAR_FIRST_DATE_PATTERN
        );
        ExceptionUtils.areValidDates(datesToValidate);

        this.passportNumber = passportNumber.toUpperCase();
        this.issueDate = SQLUtils.toDate(issueDate);
        this.expiryDate = SQLUtils.toDate(expiryDate);
        this.personInfoId = personInfoId;
    }

    public Passport(String passportNumber, Date issueDate, Date expiryDate) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportNumber = passportNumber.toUpperCase();
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public Passport(String passportNumber, String issueDate, String expiryDate) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);
        Map<String, String> datesToValidate = Map.of(
                issueDate, StringConstants.YEAR_FIRST_DATE_PATTERN,
                expiryDate, StringConstants.YEAR_FIRST_DATE_PATTERN
        );
        ExceptionUtils.areValidDates(datesToValidate);

        this.passportNumber = passportNumber.toUpperCase();
        this.issueDate = SQLUtils.toDate(issueDate);
        this.expiryDate = SQLUtils.toDate(expiryDate);
    }


    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportNumber = passportNumber.toUpperCase();
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setIssueDate(String issueDate) {
        ExceptionUtils.isValidDateString(
                issueDate,
                StringConstants.YEAR_FIRST_DATE_PATTERN
        );

        this.expiryDate = SQLUtils.toDate(issueDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExceptionUtils.isValidDateString(
                expiryDate,
                StringConstants.YEAR_FIRST_DATE_PATTERN
        );

        this.expiryDate = SQLUtils.toDate(expiryDate);
    }


    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = Passport.class;
        String[] fieldNames = {
                "passportNumber",
                "issueDate",
                "expiryDate",
                "personInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
