package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.sql.Date;

public class PersonInfo {
    private int personInfoId;
    private String surname;
    private String givenName;
    private String middleName;
    private Date birthdate;
    private String sex;
    private int addressId;
    private int contactInfoId;

    public PersonInfo() {
    }

    public PersonInfo(int personInfoId, String surname, String givenName, String middleName, Date birthdate, String sex, int addressId, int contactInfoId) {
        this.personInfoId = personInfoId;
        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.sex = sex;
        this.addressId = addressId;
        this.contactInfoId = contactInfoId;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getContactInfoId() {
        return contactInfoId;
    }

    public void setContactInfoId(int contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "personInfoId",
                "surname",
                "givenName",
                "middleName",
                "birthdate",
                "sex",
                "addressId",
                "contactInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

/*
CREATE TABLE
    person_info (
        person_info_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        surname VARCHAR(45) NOT NULL,
        given_name VARCHAR(45) NOT NULL,
        middle_name VARCHAR(45),
        birthdate DATE NOT NULL,
        sex VARCHAR(1) NOT NULL
            CHECK (
                sex IN ('M', 'F')
            ),
        address_id INT UNSIGNED,
        contact_info_id INT UNSIGNED,
        PRIMARY KEY(person_info_id)
    );

    ALTER TABLE person_info
    ADD
        FOREIGN KEY(address_id) REFERENCES addresses(address_id),
    ADD
        FOREIGN KEY(contact_info_id) REFERENCES contact_info(contact_info_id);
*/
