package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PersonInfo {
    private int personInfoId;
    private String surname;
    private String givenName;
    private String middleName;
    private Date birthdate;
    private String sex;

    private Set<Address> addresses;
    private Set<PhoneNumber> phoneNumbers;
    private Set<EmailAddress> emailAddresses;

    final private static int NAME_MAX_WIDTH = 45;
    final private static int SEX_MAX_WIDTH = 1;

    public PersonInfo() {
        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
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

        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
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

        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
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

        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
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

        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
    }

    public PersonInfo(int personInfoId) {
        this.personInfoId = personInfoId;

        this.addresses = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
        this.emailAddresses = new HashSet<>();
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

    public Set<Address> getAddresses() {
        if (addresses == null) {
            addresses = new HashSet<>();
        }
        return addresses;
    }

    public Set<PhoneNumber> getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new HashSet<>();
        }
        return phoneNumbers;
    }

    public Set<EmailAddress> getEmailAddresses() {
        if (emailAddresses == null) {
            emailAddresses = new HashSet<>();
        }
        return emailAddresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setEmailAddresses(Set<EmailAddress> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new HashSet<>();
        }
        this.addresses.add(address);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (this.phoneNumbers == null) {
            this.phoneNumbers = new HashSet<>();
        }
        this.phoneNumbers.add(phoneNumber);
    }

    public void addEmailAddress(EmailAddress emailAddress) {
        if (this.emailAddresses == null) {
            this.emailAddresses = new HashSet<>();
        }
        this.emailAddresses.add(emailAddress);
    }

    public boolean removeAddress(Address address) {
        return this.addresses != null && this.addresses.remove(address);
    }

    public boolean removePhoneNumber(PhoneNumber phoneNumber) {
        return this.phoneNumbers != null && this.phoneNumbers.remove(phoneNumber);
    }

    public boolean removeEmailAddress(EmailAddress emailAddress) {
        return this.emailAddresses != null && this.emailAddresses.remove(emailAddress);
    }

    public EmailAddress getEmailAddressByEmailAddress(String email) {
        return getEmailAddresses().stream()
                .filter(emailAddress -> emailAddress.getEmailAddress().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.PERSON_INFO;
        String[] fieldNames = {
                "personInfoId",
                "surname",
                "givenName",
                "middleName",
                "birthdate",
                "sex",
                "addresses",
                "phoneNumbers",
                "emailAddresses"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
