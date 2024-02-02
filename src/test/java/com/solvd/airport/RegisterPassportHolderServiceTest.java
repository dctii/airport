package com.solvd.airport;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.exception.InvalidPhoneNumberException;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PassportDAO;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.persistence.PhoneNumberDAO;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RegisterPassportHolderServiceTest {
    private static final Logger LOGGER =
            LogManager.getLogger(RegisterPassportHolderServiceTest.class);

    private RegisterPassportHolderService registerPassportHolderService;
    private PersonInfoDAO personInfoDAO;
    private PassportDAO passportDAO;
    private AddressDAO addressDAO;
    private PhoneNumberDAO phoneNumberDAO;
    private EmailAddressDAO emailAddressDAO;

    private List<Integer> createdPersonIds;
    private List<Integer> createdPassportIds;
    private List<Integer> createdAddressIds;
    private List<Integer> createdPhoneNumberIds;
    private List<Integer> createdEmailAddressIds;


    @BeforeClass
    public void setUp() {
        // instantiate DAOs
        personInfoDAO = DataAccessProvider.getPersonInfoDAO();
        passportDAO = DataAccessProvider.getPassportDAO();
        addressDAO = DataAccessProvider.getAddressDAO();
        phoneNumberDAO = DataAccessProvider.getPhoneNumberDAO();
        emailAddressDAO = DataAccessProvider.getEmailAddressDAO();

        // Instantiate lists to collect IDs for deletion later
        createdPersonIds = new ArrayList<>();
        createdPassportIds = new ArrayList<>();
        createdAddressIds = new ArrayList<>();
        createdPhoneNumberIds = new ArrayList<>();
        createdEmailAddressIds = new ArrayList<>();

        registerPassportHolderService = new RegisterPassportHolderServiceImpl();

    }

    @AfterClass
    public void tearDown() {
        // Iterate over the list of created person IDs and delete each one
        createdPersonIds.forEach(personId -> personInfoDAO.delete(personId));
        createdPersonIds.clear();

        createdPassportIds.forEach(passportId -> passportDAO.delete(passportId));
        createdPassportIds.clear();

        createdAddressIds.forEach(addressId -> addressDAO.delete(addressId));
        createdAddressIds.clear();

        createdPhoneNumberIds.forEach(phoneNumberId -> phoneNumberDAO.delete(phoneNumberId));
        createdPhoneNumberIds.clear();

        createdEmailAddressIds.forEach(emailAddressId -> emailAddressDAO.delete(emailAddressId));
        createdEmailAddressIds.clear();
    }

    @DataProvider
    public Object[][] newValidPeopleInfo() {
        return new Object[][]{
                {"Doe", "John", "Joseph", "1988-12-14", "M"},
                {"Jackson", "Janet", "Jenae", "1999-12-15", "F"}
        };
    }

    @DataProvider
    public Object[][] newValidPeopleInfoWithNoneAsMiddleName() {
        return new Object[][]{
                {"Journeyman", "John", "none", "1988-12-14", "M"},
                {"Journeyman", "Janet", "none", "1999-12-15", "F"}
        };
    }

    @DataProvider
    public Object[][] newValidPeopleInfoWithEmptyMiddleNameString() {
        return new Object[][]{
                {"Quixote", "Don", "", "1988-12-14", "M"},
                {"Quixote", "Donna", "", "1999-12-15", "F"}
        };
    }

    @DataProvider
    public Object[][] newPeopleInfoWithInvalidDateString() {
        return new Object[][]{
                {"John", "Jackson", "Doe", "1981214", "M"},
                {"Janet", "Jenae", "Doe", "abc123", "F"}
        };
    }

    @DataProvider
    public Object[][] newPeopleInfoWithInvalidSexString() {
        return new Object[][]{
                {"Johnathan", "Jackson", "Parker", "1988-12-14", "X"},
                {"Jeanie", "Jenae", "Johnson", "1999-12-15", "KK"}
        };
    }

    @DataProvider
    public Object[][] newValidAddresses() {
        return new Object[][]{
                {"123 Orange Street", "Los Angeles", "90210", "US"},
                {"456 Apple Street", "Los Angeles", "90210", "US"},
        };
    }

    @DataProvider
    public Object[][] newAddressesWithInvalidCountryCode() {
        return new Object[][]{
                {"456 Orange Street", "Los Angeles", "90210", "XYZ"},
                {"123 Apple Street", "Los Angeles", "90210", "56"},
                {"789 Apple Lane", "Los Angeles", "90210", "U"},
        };
    }


    @DataProvider
    public Object[][] newValidPhoneNumbers() {
        return new Object[][]{
                {"+17147289299"},
                {"+12134213866"},
        };
    }

    @DataProvider
    public Object[][] newPhoneNumbersWithoutInternationalFormat() {
        return new Object[][]{
                {"17147289313"},
                {"12134213866"},
        };
    }

    @DataProvider
    public Object[][] newInvalidPhoneNumbers() {
        return new Object[][]{
                {"+1"},
                {"+154795"}
        };
    }

    @DataProvider
    public Object[][] newValidEmailAddresses() {
        return new Object[][]{
                {"john_doe@example.com"},
                {"jane_doe@example.com"},
        };
    }

    @DataProvider
    public Object[][] newEmailAddressesWithoutAtSign() {
        return new Object[][]{
                {"john_doeexample.com"},
                {"jane_doeexample.com"},
        };
    }

    @DataProvider
    public Object[][] newEmailAddressesWithInvalidDomainName() {
        return new Object[][]{
                {"john_johnson@examplecom"},
                {"jane_jameson@examplecom"},
        };
    }


    @DataProvider
    public Object[][] newPersonAndPassportData() {
        return new Object[][]{
                {
                        new PersonInfo("Paul", "Jackson", "Doe", "1988-12-14", "M"),
                        "USA12345678", "2010-01-01", "2025-01-01"
                },
                {
                        new PersonInfo("Alice", "Ann", "Smith", "1990-05-20", "F"),
                        "USA87654321", "2012-05-20", "2030-05-20"
                }
        };
    }

    @DataProvider
    public Object[][] newPersonAndAddressData() {
        return new Object[][]{
                {
                        new PersonInfo("Aaron", "Jackson", "Doe", "1988-12-14", "M"),
                        new Address("1231 Pear Street", "Los Angeles", "90210", "US")
                },
                {
                        new PersonInfo("Katelyn", "Ann", "Smith", "1990-05-20", "F"),
                        new Address("3121 Papaya Street", "Los Angeles", "90210", "US")
                }
        };
    }

    @DataProvider
    public Object[][] newPersonAndPhoneNumberData() {
        return new Object[][]{
                // Each row has data for one PersonInfo and one Passport
                {
                        new PersonInfo("Gerald", "Perry", "Doe", "1988-12-14", "M"),
                        new PhoneNumber("+19095973120")
                },
                {
                        new PersonInfo("Helen", "Karen", "Monroe", "1990-05-20", "F"),
                        new PhoneNumber("+19097952130")
                }
        };
    }

    @DataProvider
    public Object[][] newPersonAndEmailAddressData() {
        return new Object[][]{
                // Each row has data for one PersonInfo and one Passport
                {
                        new PersonInfo("Thomas", "Thompson", "Doe", "1988-12-14", "M"),
                        new EmailAddress("thomas_thompson@example.com")
                },
                {
                        new PersonInfo("Karen", "Ann", "Parker", "1990-05-20", "F"),
                        new EmailAddress("karen_parker@example.com")
                }
        };
    }

    @DataProvider
    public Object[][] newPassportHolderData() {
        return new Object[][]{
                {
                        new PersonInfo("Michael", "James", "Smith", "1990-04-22", "M"),
                        new Passport("USA9875468", "2022-01-15", "2032-01-14"),
                        new Address("100 Main Street", "Los Angeles", "90001", "US"),
                        new PhoneNumber("+12137256575"),
                        new EmailAddress("michael_smith@example.com")
                },
                {
                        new PersonInfo("Emma", "Louise", "Johnson", "1995-07-10", "F"),
                        new Passport("B7654321", "2023-05-20", "2033-05-19"),
                        new Address("200 Elm Street", "Los Angeles", "90002", "US"),
                        new PhoneNumber("+12137256574"),
                        new EmailAddress("emma_johnson@example.com")
                }
        };
    }

    @Test(
            description = "Take valid input for PersonInfo and create a new row in person_info in the MySQL database",
            dataProvider = "newValidPeopleInfo"
    )
    public void createPersonInfoWithValidData(
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        surnameString,
                        givenNameString,
                        middleNameString,
                        birthdateString,
                        sexString
                );

        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);
    }

    @Test(
            description = "Take valid input for PersonInfo where 'none' is the middle name and create a new row in person_info in the MySQL database",
            dataProvider = "newValidPeopleInfoWithNoneAsMiddleName"
    )
    public void createPersonInfoWithNoneAsMiddleName(
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfoWithNoneAsMiddleName(
                        personInfoDAO,
                        surnameString,
                        givenNameString,
                        middleNameString,
                        birthdateString,
                        sexString
                );

        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);
    }

    @Test(
            description = "Take valid input for PersonInfo where empty string is the middle name and create a new row in person_info in the MySQL database",
            dataProvider = "newValidPeopleInfoWithNoneAsMiddleName"
    )
    public void createPersonInfoWithEmptyStringAsMiddleName(
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfoWithEmptyStringAsMiddleName(
                        personInfoDAO,
                        surnameString,
                        givenNameString,
                        middleNameString,
                        birthdateString,
                        sexString
                );

        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);
    }

    @Test(
            description = "Take invalid PersonInfo date string has incorrect format and throws DateTimeParseException",
            dataProvider = "newPeopleInfoWithInvalidDateString",
            expectedExceptions = DateTimeParseException.class,
            expectedExceptionsMessageRegExp = "Text '.*' could not be parsed.*"
    )
    public void tryCreationPersonInfoWithInvalidDateString(
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        TestUtils.createAndAssertPersonInfoWithInvalidDateString(
                personInfoDAO,
                surnameString,
                givenNameString,
                middleNameString,
                birthdateString,
                sexString
        );
    }

    @Test(
            description = "Take invalid PersonInfo sex string is not 'M' or 'F' or is greater than length of 2 and throws IllegalArgumentException",
            dataProvider = "newPeopleInfoWithInvalidSexString",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Input validation failed"
    )
    public void tryCreationPersonInfoWithInvalidSexString(
            String surnameString,
            String givenNameString,
            String middleNameString,
            String birthdateString,
            String sexString
    ) {
        TestUtils.createAndAssertPersonInfoWithInvalidSexString(
                personInfoDAO,
                surnameString,
                givenNameString,
                middleNameString,
                birthdateString,
                sexString
        );
    }


    @Test(
            description = "Take valid input for Address and create a new row in addresses in the MySQL database",
            dataProvider = "newValidAddresses"
    )
    public void createAddressWithValidData(
            String streetString, String cityString, String postalCodeString, String countryCodeString
    ) {
        // Create Address and get addressId
        int newAddressId = TestUtils.createAndAssertAddress(
                addressDAO,
                streetString,
                cityString,
                postalCodeString,
                countryCodeString
        );

        Assert.assertTrue(
                newAddressId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdAddressIds.add(newAddressId);
    }

    @Test(
            description = "Take invalid country code string for Address and try to create an instance of it",
            dataProvider = "newAddressesWithInvalidCountryCode",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Invalid country code: .*"

    )
    public void tryCreationAddressWithInvalidCountryCode(
            String streetString, String cityString, String postalCodeString, String countryCodeString
    ) {
        TestUtils.createAndAssertAddressWithInvalidCountryCode(
                addressDAO,
                streetString,
                cityString,
                postalCodeString,
                countryCodeString
        );
    }


    @Test(
            description = "Take valid input for PhoneNumber and create a new row in phone_numbers in the MySQL database",
            dataProvider = "newValidPhoneNumbers"
    )
    public void createPhoneNumberWithValidData(
            String phoneNumberString
    ) {
        // Create PhoneNumber and get phoneNumberId
        int newPhoneNumberId = TestUtils.createAndAssertPhoneNumber(phoneNumberDAO, phoneNumberString);

        Assert.assertTrue(
                newPhoneNumberId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdPhoneNumberIds.add(newPhoneNumberId);
    }

    @Test(
            description = "Take a phone number string not formatted as an international number for PhoneNumber and try to create an instance",
            dataProvider = "newPhoneNumbersWithoutInternationalFormat",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Data doesn't match validator expectations"

    )
    public void tryCreationPhoneNumberWithoutInternationalFormat(
            String phoneNumberString
    ) {
        TestUtils.createAndAssertPhoneNumberWithoutInternationalFormat(
                phoneNumberDAO,
                phoneNumberString
        );
    }

    @Test(
            description = "Take a phone number string that is an invalid number for PhoneNumber and try to create an instance",
            dataProvider = "newInvalidPhoneNumbers",
            expectedExceptions = InvalidPhoneNumberException.class,
            expectedExceptionsMessageRegExp = "Invalid phone number format|Unable to parse phone number stringError type: NOT_A_NUMBER. The string supplied did not seem to be a phone number."
    )
    public void tryCreationPhoneNumberWithInvalidPhoneNumber(
            String phoneNumberString
    ) {
        TestUtils.createAndAssertPhoneNumberWithInvalidNumber(
                phoneNumberDAO,
                phoneNumberString
        );
    }

    @Test(
            description = "Take valid input for EmailAddress and create a new row in email_addresses in the MySQL database",
            dataProvider = "newValidEmailAddresses"
    )
    // Create EmailAddress and get emailAddressId
    public void createEmailAddressWithValidData(String emailAddressString) {
        int newEmailAddressId = TestUtils.createAndAssertEmailAddress(emailAddressDAO, emailAddressString);

        Assert.assertTrue(
                newEmailAddressId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        createdEmailAddressIds.add(newEmailAddressId);
    }

    @Test(
            description = "Take email address string without '@' sign for EmailAddress and try to create an instance",
            dataProvider = "newEmailAddressesWithoutAtSign",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Input validation failed"
    )
    // Create EmailAddress and get emailAddressId
    public void tryCreationEmailAddressWithoutAtSign(String emailAddressString) {
        TestUtils.createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddressString
        );

    }

    @Test(
            description = "Take email address string with a domain that doesn't have a '.' between the name and TLD for EmailAddress and try to create an instance",
            dataProvider = "newEmailAddressesWithInvalidDomainName",
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Input validation failed"
    )
    // Create EmailAddress and get emailAddressId
    public void tryCreationEmailAddressWithInvalidDomainName(String emailAddressString) {
        TestUtils.createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddressString
        );

    }

    @Test(
            description = "Take valid input for PersonInfo and Passport and create a new row in person_info and passports in the MySQL database",
            dataProvider = "newPersonAndPassportData"
    )
    public void createPassportWithValidData(
            PersonInfo personInfo,
            String passportNumString,
            String issueDateString,
            String expiryDateString
    ) {
        // Create Person and get personInfoId
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        personInfo.getSurname(),
                        personInfo.getGivenName(),
                        personInfo.getMiddleName(),
                        personInfo.getBirthdate().toString(),
                        personInfo.getSex()
                );
        Assert.assertTrue(newPersonInfoId > 0, "The PersonInfo ID should be a positive number after successful create.");
        createdPersonIds.add(newPersonInfoId);

        // Create Passport and get passportId
        int newPassportId = TestUtils.createAndAssertPassport(
                passportDAO,
                passportNumString,
                issueDateString,
                expiryDateString,
                newPersonInfoId
        );

        Assert.assertTrue(
                newPassportId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        createdPassportIds.add(newPassportId);
    }


    @Test(
            description = "Take valid input for PersonInfo and Address, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndAddressData"
    )
    public void createPersonAddressAssociationWithValidData(
            PersonInfo personInfo,
            Address address
    ) {
        // create new person
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        personInfo.getSurname(),
                        personInfo.getGivenName(),
                        personInfo.getMiddleName(),
                        personInfo.getBirthdate().toString(),
                        personInfo.getSex()
                );
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);

        // create new address
        int newAddressId = TestUtils.createAndAssertAddress(
                addressDAO,
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountryCode()
        );

        Assert.assertTrue(
                newAddressId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdAddressIds.add(newAddressId);


        int newPersonAddressId = addressDAO.createPersonAssociation(
                newPersonInfoId,
                newAddressId
        );

        Assert.assertTrue(
                newPersonAddressId > 0,
                "The person_address_id should be a positive number after successful create"
        );
    }


    @Test(
            description = "Take valid input for PersonInfo and Address, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndPhoneNumberData"
    )
    public void createPersonPhoneNumberAssociationWithValidData(
            PersonInfo personInfo,
            PhoneNumber phoneNumber
    ) {

        // Create PersonInfo
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        personInfo.getSurname(),
                        personInfo.getGivenName(),
                        personInfo.getMiddleName(),
                        personInfo.getBirthdate().toString(),
                        personInfo.getSex()
                );
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);


        int newPhoneNumberId = TestUtils.createAndAssertPhoneNumber(phoneNumberDAO, phoneNumber.getPhoneNumber());

        Assert.assertTrue(
                newPhoneNumberId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdPhoneNumberIds.add(newPhoneNumberId);

        int newPersonPhoneNumberId = phoneNumberDAO.createPersonAssociation(newPersonInfoId, newPhoneNumberId);

        Assert.assertTrue(
                newPersonPhoneNumberId > 0,
                "The person_phone_number_id should be a positive number after successful create"
        );
    }


    @Test(
            description = "Take valid input for PersonInfo and EmailAddress, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndEmailAddressData"
    )
    public void createPersonEmailAddressAssociationWithValidData(
            PersonInfo personInfo,
            EmailAddress emailAddress
    ) {
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        personInfo.getSurname(),
                        personInfo.getGivenName(),
                        personInfo.getMiddleName(),
                        personInfo.getBirthdate().toString(),
                        personInfo.getSex()
                );
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
        createdPersonIds.add(newPersonInfoId);

        int newEmailAddressId = TestUtils.createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddress.getEmailAddress()
        );

        Assert.assertTrue(
                newEmailAddressId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        createdEmailAddressIds.add(newEmailAddressId);

        int newPersonEmailAddressId = emailAddressDAO.createPersonAssociation(
                newPersonInfoId,
                newEmailAddressId
        );

        Assert.assertTrue(
                newPersonEmailAddressId > 0,
                "The person_email_address_id should be a positive number after successful create"
        );
    }

    @Test(
            description = "Take valid input for passport holder and create relevant data in MySQL database",
            dataProvider = "newPassportHolderData",
            enabled = true
    )
    public void registerPassportHolderWithValidData(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    ) {
        // Create Person and get personInfoId
        int newPersonInfoId =
                TestUtils.createAndAssertPersonInfo(
                        personInfoDAO,
                        personInfo.getSurname(),
                        personInfo.getGivenName(),
                        personInfo.getMiddleName(),
                        personInfo.getBirthdate().toString(),
                        personInfo.getSex()
                );
        Assert.assertTrue(newPersonInfoId > 0, "The PersonInfo ID should be a positive number after successful create.");
        createdPersonIds.add(newPersonInfoId);

        // Create Passport and get passportId
        int newPassportId = TestUtils.createAndAssertPassport(
                passportDAO,
                passport.getPassportNumber(),
                passport.getIssueDate().toString(),
                passport.getExpiryDate().toString(),
                newPersonInfoId
        );

        Assert.assertTrue(
                newPassportId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        createdPassportIds.add(newPassportId);

        // Create new address and get addressId
        int newAddressId = TestUtils.createAndAssertAddress(
                addressDAO,
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountryCode()
        );

        Assert.assertTrue(
                newAddressId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdAddressIds.add(newAddressId);

        // Create new phone number and get phoneNumberId
        int newPhoneNumberId = TestUtils.createAndAssertPhoneNumber(phoneNumberDAO, phoneNumber.getPhoneNumber());

        Assert.assertTrue(
                newPhoneNumberId > 0,
                "The Address ID should be a positive number after successful create."
        );
        createdPhoneNumberIds.add(newPhoneNumberId);

        // Create new email address and get emailAddressId
        int newEmailAddressId = TestUtils.createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddress.getEmailAddress()
        );

        Assert.assertTrue(
                newEmailAddressId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        createdEmailAddressIds.add(newEmailAddressId);

        // Create PersonInfo-<Contact> associations

        // PersonInfo-Address association
        int newPersonAddressId = addressDAO.createPersonAssociation(
                newPersonInfoId,
                newAddressId
        );

        Assert.assertTrue(
                newPersonAddressId > 0,
                "The person_address_id should be a positive number after successful create"
        );

        // PersonInfo-PhoneNumber association
        int newPersonPhoneNumberId = phoneNumberDAO.createPersonAssociation(newPersonInfoId, newPhoneNumberId);

        Assert.assertTrue(
                newPersonPhoneNumberId > 0,
                "The person_phone_number_id should be a positive number after successful create"
        );

        // PersonInfo-EmailAddress association
        int newPersonEmailAddressId = emailAddressDAO.createPersonAssociation(
                newPersonInfoId,
                newEmailAddressId
        );

        Assert.assertTrue(
                newPersonEmailAddressId > 0,
                "The person_email_address_id should be a positive number after successful create"
        );

        registerPassportHolderService.displayRegisteredInfo(
                personInfo,
                passport,
                address,
                phoneNumber,
                emailAddress
        );

    }

}
