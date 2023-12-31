package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class PersonInfo {
    private int personInfoId;
    private String surname;
    private String givenName;
    private String middleName;
    private Date birthdate;
    private String sex;

    final private static int NAME_MAX_WIDTH = 45;
    final private static int SEX_MAX_WIDTH = 1;

    public PersonInfo() {
    }

    public PersonInfo(int personInfoId, String surname, String givenName,
                      String middleName, Date birthdate, String sex) {
        Map<String, Integer> lengths = new HashMap<>();
        lengths.put(surname, NAME_MAX_WIDTH);
        lengths.put(givenName, NAME_MAX_WIDTH);
        lengths.put(sex, SEX_MAX_WIDTH);
        if (middleName != null) {
            lengths.put(middleName, NAME_MAX_WIDTH);
        }
        ExceptionUtils.areStringLengthsValid(lengths);

        ExceptionUtils.isValidSex(sex);


        this.personInfoId = personInfoId;
        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.sex = sex.toUpperCase();
    }

    public PersonInfo(int personInfoId, String surname, String givenName,
                      String middleName, String birthdate, String sex) {
        Map<String, Integer> lengths = new HashMap<>();
        lengths.put(surname, NAME_MAX_WIDTH);
        lengths.put(givenName, NAME_MAX_WIDTH);
        lengths.put(sex, SEX_MAX_WIDTH);
        if (middleName != null) {
            lengths.put(middleName, NAME_MAX_WIDTH);
        }
        ExceptionUtils.areStringLengthsValid(lengths);

        ExceptionUtils.isValidDateString(
                birthdate,
                StringConstants.YEAR_FIRST_DATE_PATTERN
        );
        ExceptionUtils.isValidSex(sex);


        this.personInfoId = personInfoId;
        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = SQLUtils.toDate(birthdate);
        this.sex = sex.toUpperCase();
    }

    public PersonInfo(String surname, String givenName, String middleName,
                      String birthdate, String sex) {
        Map<String, Integer> lengths = new HashMap<>();
        lengths.put(surname, NAME_MAX_WIDTH);
        lengths.put(givenName, NAME_MAX_WIDTH);
        lengths.put(sex, SEX_MAX_WIDTH);
        if (middleName != null) {
            lengths.put(middleName, NAME_MAX_WIDTH);
        }
        ExceptionUtils.areStringLengthsValid(lengths);
        ExceptionUtils.isValidDateString(
                birthdate,
                StringConstants.YEAR_FIRST_DATE_PATTERN
        );

        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = SQLUtils.toDate(birthdate);
        this.sex = sex.toUpperCase();
    }

    public PersonInfo(String surname, String givenName, String middleName,
                      Date birthdate, String sex) {
        Map<String, Integer> lengths = new HashMap<>();
        lengths.put(surname, NAME_MAX_WIDTH);
        lengths.put(givenName, NAME_MAX_WIDTH);
        lengths.put(sex, SEX_MAX_WIDTH);
        if (middleName != null) {
            lengths.put(middleName, NAME_MAX_WIDTH);
        }
        ExceptionUtils.areStringLengthsValid(lengths);

        this.surname = surname;
        this.givenName = givenName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.sex = sex.toUpperCase();
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
        ExceptionUtils.isStringLengthValid(surname, NAME_MAX_WIDTH);

        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        ExceptionUtils.isStringLengthValid(givenName, NAME_MAX_WIDTH);

        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if (middleName != null) {
            ExceptionUtils.isStringLengthValid(middleName, NAME_MAX_WIDTH);
        }

        this.middleName = middleName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setBirthdate(String birthdate) {
        ExceptionUtils.isValidDateString(
                birthdate,
                StringConstants.YEAR_FIRST_DATE_PATTERN
        );

        this.birthdate = SQLUtils.toDate(birthdate);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        ExceptionUtils.isStringLengthValid(sex, SEX_MAX_WIDTH);
        ExceptionUtils.isValidSex(sex);

        this.sex = sex.toUpperCase();
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
