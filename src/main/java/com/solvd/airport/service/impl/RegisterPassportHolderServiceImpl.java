package com.solvd.airport.service.impl;

import com.solvd.airport.domain.*;
import com.solvd.airport.persistence.*;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.StringConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterPassportHolderServiceImpl implements RegisterPassportHolderService {
    private static final Logger LOGGER = LogManager.getLogger(RegisterPassportHolderServiceImpl.class);
    private final PersonInfoDAO personInfoDAO = DataAccessProvider.getPersonInfoDAO();
    private final PassportDAO passportDAO = DataAccessProvider.getPassportDAO();
    private final AddressDAO addressDAO = DataAccessProvider.getAddressDAO();
    private final PhoneNumberDAO phoneNumberDAO = DataAccessProvider.getPhoneNumberDAO();
    private final EmailAddressDAO emailAddressDAO = DataAccessProvider.getEmailAddressDAO();
    private final PersonAddressDAO personAddressDAO = DataAccessProvider.getPersonAddressDao();
    private final PersonPhoneNumberDAO personPhoneNumberDAO = DataAccessProvider.getPersonPhoneNumberDAO();
    private final PersonEmailAddressDAO personEmailAddressDAO = DataAccessProvider.getPersonEmailAddressDAO();

    @Override
    public void registerPassportHolder(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    ) {
        // create person
        personInfoDAO.createPersonInfo(personInfo);

        // prepare person_info_id
        int personInfoId = personInfo.getPersonInfoId();

        // Set person_info_id to passport
        passport.setPersonInfoId(personInfoId);
        passportDAO.createPassport(passport);

        // create contact info
        addressDAO.createAddress(address);
        phoneNumberDAO.createPhoneNumber(phoneNumber);
        emailAddressDAO.createEmailAddress(emailAddress);

        // prepare *_id values to associate with person_info_id
        int addressId = address.getAddressId();
        int phoneNumberId = phoneNumber.getPhoneNumberId();
        int emailAddressId = emailAddress.getEmailAddressId();

        // associate contact information with person_info_id
        PersonAddress personAddress = new PersonAddress(personInfoId, addressId);
        personAddressDAO.createPersonAddress(personAddress);
        PersonPhoneNumber personPhoneNumber = new PersonPhoneNumber(personInfoId, phoneNumberId);
        personPhoneNumberDAO.createPersonPhoneNumber(personPhoneNumber);
        PersonEmailAddress personEmailAddress = new PersonEmailAddress(personInfoId, emailAddressId);
        personEmailAddressDAO.createPersonEmailAddress(personEmailAddress);

        displayRegisteredInfo(personInfo, passport, address, phoneNumber, emailAddress);
    }

    private void displayRegisteredInfo(PersonInfo personInfo, Passport passport, Address address, PhoneNumber phoneNumber, EmailAddress emailAddress) {
        // Display Person Info
        LOGGER.info(StringConstants.NEWLINE);

        LOGGER.info("{}{}=== Registered Person Information: ==={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
        LOGGER.info(
                "Name: " + StringUtils.joinWith(
                        StringConstants.SINGLE_WHITESPACE,
                        personInfo.getGivenName(), personInfo.getMiddleName(), personInfo.getSurname()
                ).replace(StringConstants.DOUBLE_WHITESPACE, StringConstants.SINGLE_WHITESPACE)
        );
        LOGGER.info("Birthdate: " + personInfo.getBirthdate());
        LOGGER.info("Sex: " + personInfo.getSex());

        LOGGER.info(StringConstants.NEWLINE);

        // Display Passport Info
        LOGGER.info("{}{}= Passport Information ={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
        LOGGER.info("Passport Number: " + passport.getPassportNumber());
        LOGGER.info("Issue Date: " + passport.getIssueDate());
        LOGGER.info("Expiration Date: " + passport.getExpiryDate());

        LOGGER.info(StringConstants.NEWLINE);

        // Display Phone Number
        LOGGER.info("{}{}= Address ={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
        LOGGER.info("Street: " + address.getStreet());
        LOGGER.info("City: " + address.getCity());
        LOGGER.info("Postal Code: " + address.getPostalCode());
        LOGGER.info("Country: " + address.getCountryCode());

        LOGGER.info(StringConstants.NEWLINE);

        // Display Phone Number
        LOGGER.info("{}{}= Phone Number ={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
        LOGGER.info("Phone Number: " + phoneNumber.getPhoneNumber());

        LOGGER.info(StringConstants.NEWLINE);

        // Display Email Address
        LOGGER.info("{}{}= Email Address ={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
        LOGGER.info("Email Address: " + emailAddress.getEmailAddress());

        LOGGER.info(StringConstants.NEWLINE);
    }
}
