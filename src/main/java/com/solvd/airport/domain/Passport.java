package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Passport {
    private int passportId;
    private String passportNumber;
    private Date issueDate;
    private Date expiryDate;
    private Integer personInfoId;

    private Set<Booking> bookings;
    final static private int PASSPORT_NUMBER_MAX_WIDTH = 45;

    public Passport() {
    }

    public Passport(String passportNumber, Date issueDate, Date expiryDate, int personInfoId) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportNumber = passportNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.personInfoId = personInfoId;
    }

    public Passport(int passportId, String passportNumber, Date issueDate, Date expiryDate, int personInfoId) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportId = passportId;
        this.passportNumber = passportNumber;
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

    public Passport(int passportId, String passportNumber, String issueDate, String expiryDate, int personInfoId) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);
        Map<String, String> datesToValidate = Map.of(
                issueDate, StringConstants.YEAR_FIRST_DATE_PATTERN,
                expiryDate, StringConstants.YEAR_FIRST_DATE_PATTERN
        );
        ExceptionUtils.areValidDates(datesToValidate);

        this.passportId = passportId;
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

    public Passport(int passportId, String passportNumber, Date issueDate, Date expiryDate) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);

        this.passportId = passportId;
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

    public Passport(int passportId, String passportNumber, String issueDate, String expiryDate) {
        ExceptionUtils.isStringLengthValid(passportNumber, PASSPORT_NUMBER_MAX_WIDTH);
        Map<String, String> datesToValidate = Map.of(
                issueDate, StringConstants.YEAR_FIRST_DATE_PATTERN,
                expiryDate, StringConstants.YEAR_FIRST_DATE_PATTERN
        );
        ExceptionUtils.areValidDates(datesToValidate);

        this.passportId = passportId;
        this.passportNumber = passportNumber.toUpperCase();
        this.issueDate = SQLUtils.toDate(issueDate);
        this.expiryDate = SQLUtils.toDate(expiryDate);
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
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


    public Integer getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    public Set<Booking> getBookings() {
        if (bookings == null) {
            bookings = new HashSet<>();
        }
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (this.bookings == null) {
            this.bookings = new HashSet<>();
        }
        this.bookings.add(booking);
    }

    public boolean removeBooking(Booking booking) {
        return this.bookings != null && this.bookings.remove(booking);
    }


    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.PASSPORT;
        String[] fieldNames = {
                "passportId",
                "passportNumber",
                "issueDate",
                "expiryDate",
                "personInfoId",
                "bookings"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
