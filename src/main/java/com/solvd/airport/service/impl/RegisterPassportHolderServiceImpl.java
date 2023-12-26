package com.solvd.airport.service.impl;

import com.solvd.airport.domain.*;
import com.solvd.airport.persistence.mappers.*;
import com.solvd.airport.persistence.impl.*;
import com.solvd.airport.service.RegisterPassportHolderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterPassportHolderServiceImpl implements RegisterPassportHolderService {
    private static final Logger LOGGER = LogManager.getLogger(RegisterPassportHolderServiceImpl.class);
    private final PersonInfoDAO personInfoDAO = new PersonInfoDAOImpl();
    private final PassportDAO passportDAO = new PassportDAOImpl();
    private final AddressDAO addressDAO = new AddressDAOImpl();
    private final PhoneNumberDAO phoneNumberDAO = new PhoneNumberDAOImpl();
    private final EmailAddressDAO emailAddressDAO = new EmailAddressDAOImpl();
    private final PersonAddressDAO personAddressDAO = new PersonAddressDAOImpl();
    private final PersonPhoneNumberDAO personPhoneNumberDAO = new PersonPhoneNumberDAOImpl();
    private final PersonEmailAddressDAO personEmailAddressDAO = new PersonEmailAddressDAOImpl();


    @Override
    public void registerPassportHolder(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    ) {
        personInfoDAO.createPersonInfo(personInfo);
        System.out.print(personInfo.getPersonInfoId());

        passport.setPersonInfoId(personInfo.getPersonInfoId());
        passportDAO.createPassport(passport);

        Address existingAddress = addressDAO.getAddressByUniqueFields(
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountryCode()
        );

        if (existingAddress == null) {
            addressDAO.createAddress(address);
            existingAddress = address;
        }
        PersonAddress personAddress = new PersonAddress(personInfo.getPersonInfoId(), existingAddress.getAddressId());
        personAddressDAO.createPersonAddress(personAddress);

        PhoneNumber existingPhoneNumber = phoneNumberDAO.getPhoneNumberByNumber(phoneNumber.getPhoneNumber());
        if (existingPhoneNumber == null) {
            phoneNumberDAO.createPhoneNumber(phoneNumber);
            existingPhoneNumber = phoneNumber;
        }
        PersonPhoneNumber personPhoneNumber = new PersonPhoneNumber(personInfo.getPersonInfoId(), existingPhoneNumber.getPhoneNumberId());
        personPhoneNumberDAO.createPersonPhoneNumber(personPhoneNumber);

        EmailAddress existingEmailAddress = emailAddressDAO.getEmailAddressByEmail(emailAddress.getEmailAddress());
        if (existingEmailAddress == null) {
            emailAddressDAO.createEmailAddress(emailAddress);
            existingEmailAddress = emailAddress;
        }
        PersonEmailAddress personEmailAddress = new PersonEmailAddress(personInfo.getPersonInfoId(), existingEmailAddress.getEmailAddressId());
        personEmailAddressDAO.createPersonEmailAddress(personEmailAddress);

        displayRegisteredInfo(personInfo, passport, phoneNumber, emailAddress);
    }

    private void displayRegisteredInfo(PersonInfo personInfo, Passport passport, PhoneNumber phoneNumber, EmailAddress emailAddress) {
        // Display Person Info
        LOGGER.info("Registered Person Information:");
        LOGGER.info(personInfo);

        // Display Passport Info
        LOGGER.info("Registered Passport Information:");
        LOGGER.info(passport);

        // Display Phone Number
        LOGGER.info("Registered Phone Number:");
        LOGGER.info(phoneNumber.getPhoneNumber());

        // Display Email Address
        LOGGER.info("Registered Email Address:");
        LOGGER.info(emailAddress.getEmailAddress());
    }
}
