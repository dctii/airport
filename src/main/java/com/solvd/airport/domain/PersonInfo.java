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

    // TODO: Add length and precision checks from ExceptionUtils
    final private static int NAME_MAX_WIDTH = 45;
    final private static int SEX_MAX_WIDTH = 1;

    public PersonInfo() {
    }

    public PersonInfo(int personInfoId, String surname, String givenName, String middleName, Date birthdate, String sex) {
        this.personInfoId = personInfoId;
        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.sex = sex;
    }

    public PersonInfo(String surname, String givenName, String middleName, Date birthdate, String sex) {
        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.sex = sex;
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

    @Override
    public String toString() {
        Class<?> currClass = PersonInfo.class;
        String[] fieldNames = {
                "personInfoId",
                "surname",
                "givenName",
                "middleName",
                "birthdate",
                "sex"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
