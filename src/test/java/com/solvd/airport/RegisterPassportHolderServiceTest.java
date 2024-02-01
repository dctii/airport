package com.solvd.airport;

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
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.TestDataConstants;
import com.solvd.airport.util.TestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RegisterPassportHolderServiceImplTest {
    private static final Logger LOGGER =
            LogManager.getLogger(RegisterPassportHolderServiceImplTest.class);

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


    /* TODO:
        - separate into EPIC -- since 10, just for register passport holder service
        - separate into sections


     */

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

    @DataProvider
    public Object[][] newValidPeopleInfo() {
        return TestDataConstants.NEW_VALID_PEOPLE;
    }

    @Test(
            description = "Take valid input for PersonInfo and create a new row in person_info in the MySQL database",
            dataProvider = "newValidPeopleInfo"
    )
    public void testGetPersonInfoAndCreateWithValidData(
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
        createdPersonIds.add(newPersonInfoId);

        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );
    }

    @DataProvider
    public Object[][] newValidAddresses() {
        return TestDataConstants.NEW_VALID_ADDRESSES;
    }

    @Test(
            description = "Take valid input for Address and create a new row in addresses in the MySQL database",
            dataProvider = "newValidAddresses",
            enabled = true
    )
    public void testGetAddressAndCreateWithValidData(
            String streetString, String cityString, String postalCodeString, String countryCodeString
    ) {
        int newAddressId = TestUtils.createAndAssertAddress(
                addressDAO,
                streetString,
                cityString,
                postalCodeString,
                countryCodeString
        );
        createdAddressIds.add(newAddressId);

        Assert.assertTrue(
                newAddressId > 0,
                "The Address ID should be a positive number after successful create."
        );
    }

    @DataProvider
    public Object[][] newValidPhoneNumbers() {
        return TestDataConstants.NEW_VALID_PHONE_NUMBERS;
    }

    @Test(
            description = "Take valid input for PhoneNumber and create a new row in phone_numbers in the MySQL database",
            dataProvider = "newValidPhoneNumbers",
            enabled = true
    )
    public void testGetPhoneNumberAndCreateWithValidData(
            String phoneNumberString
    ) {
        int newPhoneNumberId = TestUtils.createAndAssertPhoneNumber(phoneNumberDAO, phoneNumberString);
        createdPhoneNumberIds.add(newPhoneNumberId);

        Assert.assertTrue(
                newPhoneNumberId > 0,
                "The Address ID should be a positive number after successful create."
        );
    }


    @DataProvider
    public Object[][] newEmailAddresses() {
        return TestDataConstants.NEW_VALID_EMAIL_ADDRESSES;
    }

    @Test(
            description = "Take valid input for EmailAddress and create a new row in email_addresses in the MySQL database",
            dataProvider = "newEmailAddresses",
            enabled = true
    )
    public void testGetEmailAddressAndCreateWithValidData(String emailAddressString) {
        int newEmailAddressId = TestUtils.createAndAssertEmailAddress(emailAddressDAO, emailAddressString);
        createdEmailAddressIds.add(newEmailAddressId);

        Assert.assertTrue(
                newEmailAddressId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
    }

    @DataProvider
    public Object[][] newPersonAndPassportData() {
        return TestDataConstants.NEW_VALID_PEOPLE_AND_PASSPORTS;
    }

    @Test(
            description = "Take valid input for PersonInfo and Passport and create a new row in person_info and passports in the MySQL database",
            dataProvider = "newPersonAndPassportData",
            enabled = true
    )
    public void testCreatePersonInfoAndPassportWithValidData(
            PersonInfo personInfo,
            String passportNumString,
            String issueDateString,
            String expiryDateString
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
        createdPersonIds.add(newPersonInfoId);
        Assert.assertTrue(newPersonInfoId > 0, "The PersonInfo ID should be a positive number after successful create.");

        // Create Passport
        int newPassportId = TestUtils.createAndAssertPassport(passportDAO, passportNumString, issueDateString, expiryDateString, newPersonInfoId);
        createdPassportIds.add(newPassportId);

        Assert.assertTrue(
                newPassportId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
    }

    @DataProvider
    public Object[][] newPersonAndAddressData() {
        return TestDataConstants.NEW_VALID_PEOPLE_AND_PHONE_NUMBERS;
    }

    @Test(
            description = "Take valid input for PersonInfo and Address, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndAddressData",
            enabled = true
    )
    public void testCreatePersonAddressAssociationWithValidData(
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
        createdPersonIds.add(newPersonInfoId);
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );

        // create new address
        int newAddressId = TestUtils.createAndAssertAddress(
                addressDAO,
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountryCode()
        );
        createdAddressIds.add(newAddressId);

        Assert.assertTrue(
                newAddressId > 0,
                "The Address ID should be a positive number after successful create."
        );


        int newPersonAddressId = addressDAO.createPersonAssociation(
                newPersonInfoId,
                newAddressId
        );

        Assert.assertTrue(
                newPersonAddressId > 0,
                "The person_address_id should be a positive number after successful create"
        );
    }


    @DataProvider
    public Object[][] newPersonAndPhoneNumberData() {
        return TestDataConstants.NEW_VALID_PEOPLE_AND_PHONE_NUMBERS;
    }

    @Test(
            description = "Take valid input for PersonInfo and Address, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndPhoneNumberData",
            enabled = true
    )
    public void testCreatePersonPhoneNumberAssociationWithValidData(
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
        createdPersonIds.add(newPersonInfoId);
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );


        int newPhoneNumberId = TestUtils.createAndAssertPhoneNumber(phoneNumberDAO, phoneNumber.getPhoneNumber());
        createdPhoneNumberIds.add(newPhoneNumberId);

        Assert.assertTrue(
                newPhoneNumberId > 0,
                "The Address ID should be a positive number after successful create."
        );
        int newPersonPhoneNumberId = phoneNumberDAO.createPersonAssociation(newPersonInfoId, newPhoneNumberId);

        Assert.assertTrue(
                newPersonPhoneNumberId > 0,
                "The person_phone_number_id should be a positive number after successful create"
        );
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

    @Test(
            description = "Take valid input for PersonInfo and EmailAddress, create new rows in person_info and addresses, and then in person_address in the MySQL database",
            dataProvider = "newPersonAndEmailAddressData",
            enabled = true
    )
    public void testCreatePersonEmailAddressAssociationWithValidData(
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
        createdPersonIds.add(newPersonInfoId);
        Assert.assertTrue(
                newPersonInfoId > 0,
                "The PersonInfo ID should be a positive number after successful create."
        );

        int newEmailAddressId = TestUtils.createAndAssertEmailAddress(
                emailAddressDAO,
                emailAddress.getEmailAddress()
        );
        createdEmailAddressIds.add(newEmailAddressId);

        Assert.assertTrue(
                newEmailAddressId > 0,
                "The EmailAddress ID should be a positive number after successful create"
        );
        int newPersonEmailAddressId = emailAddressDAO.createPersonAssociation(
                newPersonInfoId,
                newEmailAddressId
        );

        Assert.assertTrue(
                newPersonEmailAddressId > 0,
                "The person_email_address_id should be a positive number after successful create"
        );
    }

    // TODO - finish the service
    @DataProvider
    public Object[][] newPassportHolderData() {
        return TestDataConstants.NEW_PASSPORT_HOLDERS_DATA;
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
        // TODO put the previous logic here
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

}
