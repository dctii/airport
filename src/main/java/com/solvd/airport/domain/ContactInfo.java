package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class ContactInfo {
    private int contactInfoId;
    private String phoneNumber;
    private String email;

    public ContactInfo() {
    }

    public ContactInfo(int contactInfoId, String phoneNumber, String email) {
        this.contactInfoId = contactInfoId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public int getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(int contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "contactInfoId",
                "phoneNumber",
                "email"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    contact_info (
        contact_info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        phone_number VARCHAR(45)
            CHECK (
                LEFT(phone_number, 1) = '+'
                AND SUBSTRING(phone_number, 2) REGEXP '^[0-9]+$'
            ),
        email VARCHAR(45)
            CHECK(
                LOCATE('@', email) > 0
                AND LOCATE('@', email) > 1
                AND LOCATE('@', email) < CHAR_LENGTH(email)
            ),
        PRIMARY KEY(contact_info_id)
    );
*/
