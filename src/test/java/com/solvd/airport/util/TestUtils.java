package com.solvd.airport.util;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PassportDAO;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.persistence.PhoneNumberDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    private static final Logger LOGGER =
            LogManager.getLogger(TestUtils.class);

    private TestUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }

    public static int createAndAssertPersonInfo(
            PersonInfoDAO personInfoDAO,
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {

        PersonInfo newPersonInfo = MenuUtils.getPersonInfo(surnameString, givenNameString, middleNameString, birthdateString, sexString);

        Assert.assertNotNull(newPersonInfo, "PersonInfo instance cannot be null.");
        Assert.assertEquals(newPersonInfo.getSurname(), surnameString.toUpperCase());
        Assert.assertEquals(newPersonInfo.getGivenName(), givenNameString.toUpperCase());
        Assert.assertEquals(newPersonInfo.getMiddleName(), middleNameString.toUpperCase());
        Assert.assertEquals(newPersonInfo.getBirthdate(), SQLUtils.toDate(birthdateString));
        Assert.assertEquals(newPersonInfo.getSex(), sexString.toUpperCase());

        return personInfoDAO.create(newPersonInfo);
    }

    public static int createAndAssertPersonInfoWithNoneAsMiddleName(
            PersonInfoDAO personInfoDAO,
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {

        PersonInfo newPersonInfo = MenuUtils.getPersonInfo(surnameString, givenNameString, middleNameString, birthdateString, sexString);

        Assert.assertNotNull(newPersonInfo, "PersonInfo instance cannot be null.");
        Assert.assertEquals(newPersonInfo.getSurname(), surnameString.toUpperCase());
        Assert.assertEquals(newPersonInfo.getGivenName(), givenNameString.toUpperCase());
        Assert.assertNull(newPersonInfo.getMiddleName());
        Assert.assertEquals(newPersonInfo.getBirthdate(), SQLUtils.toDate(birthdateString));
        Assert.assertEquals(newPersonInfo.getSex(), sexString.toUpperCase());

        return personInfoDAO.create(newPersonInfo);
    }

    public static int createAndAssertPersonInfoWithEmptyStringAsMiddleName(
            PersonInfoDAO personInfoDAO,
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        return createAndAssertPersonInfoWithNoneAsMiddleName(
                personInfoDAO,
                surnameString,
                givenNameString,
                middleNameString,
                birthdateString,
                sexString
        );
    }

    public static void createAndAssertPersonInfoWithInvalidDateString(
            PersonInfoDAO personInfoDAO,
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        createAndAssertPersonInfo(
                personInfoDAO,
                surnameString,
                givenNameString,
                middleNameString,
                birthdateString,
                sexString
        );
    }

    public static void createAndAssertPersonInfoWithInvalidSexString(
            PersonInfoDAO personInfoDAO,
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        createAndAssertPersonInfo(
                personInfoDAO,
                surnameString,
                givenNameString,
                middleNameString,
                birthdateString,
                sexString
        );
    }


    public static int createAndAssertAddress(AddressDAO addressDAO, String streetString, String cityString, String postalCodeString, String countryCodeString) {
        Address newAddress = MenuUtils.getAddress(streetString, cityString, postalCodeString, countryCodeString);

        Assert.assertNotNull(newAddress, "Address instance cannot be null.");

        Assert.assertEquals(newAddress.getStreet(), streetString.toUpperCase(), "Street doesn't match or isn't uppercase");
        Assert.assertEquals(newAddress.getCity(), cityString.toUpperCase(), "City doesn't match or isn't uppercase");
        Assert.assertEquals(newAddress.getPostalCode(), postalCodeString.toUpperCase(), "Postal code doesn't match or, if alphanumeric, isn't uppercase");
        Assert.assertEquals(newAddress.getCountryCode(), countryCodeString.toUpperCase(), "Country code doesn't match or isn't upper case");
        Assert.assertNull(newAddress.getCitySubdivision(), "City subdivision isn't null when it should be");
        Assert.assertNull(newAddress.getCitySuperdivision(), "City superdivision isn't null when it should be");
        Assert.assertNull(newAddress.getTimezone(), "Address timezone isn't null when it should be");


        return addressDAO.create(newAddress);
    }

    public static void createAndAssertAddressWithInvalidCountryCode(AddressDAO addressDAO, String streetString, String cityString, String postalCodeString, String countryCodeString) {
        createAndAssertAddress(
                addressDAO,
                streetString,
                cityString,
                postalCodeString,
                countryCodeString
        );
    }

    public static int createAndAssertPhoneNumber(PhoneNumberDAO phoneNumberDAO, String phoneNumberString) {
        PhoneNumber newPhoneNumber = MenuUtils.getPhoneNumber(phoneNumberString);

        Assert.assertNotNull(newPhoneNumber, "PhoneNumber instance cannot be null.");

        Assert.assertEquals(newPhoneNumber.getPhoneNumber(), StringFormatters.sanitizePhoneNumber(phoneNumberString), "Street doesn't match or isn't uppercase");
        Assert.assertNull(newPhoneNumber.getExtension(), "Phone Extension is not null when it should be");


        return phoneNumberDAO.create(newPhoneNumber);
    }

    public static void createAndAssertPhoneNumberWithoutInternationalFormat(PhoneNumberDAO phoneNumberDAO, String phoneNumberString) {
        createAndAssertPhoneNumber(
                phoneNumberDAO,
                phoneNumberString
        );
    }

    public static void createAndAssertPhoneNumberWithInvalidNumber(PhoneNumberDAO phoneNumberDAO, String phoneNumberString) {
        createAndAssertPhoneNumber(
                phoneNumberDAO,
                phoneNumberString
        );
    }

    public static int createAndAssertEmailAddress(EmailAddressDAO emailAddressDAO, String emailAddressString) {
        EmailAddress newEmailAddress = MenuUtils.getEmailAddress(emailAddressString);

        Assert.assertNotNull(newEmailAddress, "EmailAddress cannot be null");
        Assert.assertEquals(newEmailAddress.getEmailAddress(), emailAddressString.toLowerCase(), "Email address doesn't match or isn't lowercase");

        return emailAddressDAO.create(newEmailAddress);
    }

    public static void createAndAssertEmailAddressWithoutAtSign(EmailAddressDAO emailAddressDAO, String emailAddressString) {
        createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddressString
        );
    }

    public static int createAndAssertPassport(PassportDAO passportDAO, String passportNumString, String issueDateString, String expiryDateString, int newPersonInfoId) {
        Passport newPassport = MenuUtils.getPassport(passportNumString, issueDateString, expiryDateString);
        newPassport.setPersonInfoId(newPersonInfoId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConstants.YEAR_FIRST_DATE_PATTERN);
        LocalDate newIssueDate = LocalDate.parse(issueDateString, formatter);
        LocalDate newExpiryDate = LocalDate.parse(expiryDateString, formatter);

        Assert.assertNotNull(newPassport, "Passport cannot be null");
        Assert.assertEquals(newPassport.getPassportNumber(), passportNumString.toUpperCase(), "Passport number doesn't match or isn't uppercase");
        Assert.assertEquals(newPassport.getIssueDate(), java.sql.Date.valueOf(newIssueDate), "Issue Date doesn't match");
        Assert.assertEquals(newPassport.getExpiryDate(), java.sql.Date.valueOf(newExpiryDate), "Issue Date doesn't match");

        return passportDAO.create(newPassport);
    }
}
