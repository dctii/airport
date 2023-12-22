package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.sql.Date;

public class Passport {
    private String passportNumber;
    private Date issueDate;
    private Date expiryDate;
    private int personInfoId;

    public Passport() {
    }

    public Passport(String passportNumber, Date issueDate, Date expiryDate, int personInfoId) {
        this.passportNumber = passportNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.personInfoId = personInfoId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
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


/*
CREATE TABLE
    passports (
        passport_number VARCHAR(45),
        issue_date DATE,
        expiry_date DATE,
        person_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(passport_number)
    );

    ALTER TABLE passports
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id);
*/
