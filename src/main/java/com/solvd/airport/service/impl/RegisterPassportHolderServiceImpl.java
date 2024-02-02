package com.solvd.airport.service.impl;


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
import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.StringConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterPassportHolderServiceImpl implements RegisterPassportHolderService {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.REGISTER_PASSPORT_HOLDER_SERVICE_IMPL);
    private final PersonInfoDAO personInfoDAO = DataAccessProvider.getPersonInfoDAO();
    private final PassportDAO passportDAO = DataAccessProvider.getPassportDAO();
    private final AddressDAO addressDAO = DataAccessProvider.getAddressDAO();
    private final PhoneNumberDAO phoneNumberDAO = DataAccessProvider.getPhoneNumberDAO();
    private final EmailAddressDAO emailAddressDAO = DataAccessProvider.getEmailAddressDAO();

    @Override
    public void registerPassportHolder(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    ) {
        // create person
        personInfoDAO.create(personInfo);
        addressDAO.create(address);
        phoneNumberDAO.create(phoneNumber);
        emailAddressDAO.create(emailAddress);


        // set person_info_id to passport and create the passport item in the `passports` table
        int personInfoId = personInfo.getPersonInfoId();
        passport.setPersonInfoId(personInfoId);
        passportDAO.create(passport);

        // Create contact info and associate with person_info_id
        addressDAO.createPersonAssociation(personInfoId, address.getAddressId());
        phoneNumberDAO.createPersonAssociation(personInfoId, phoneNumber.getPhoneNumberId());
        emailAddressDAO.createPersonAssociation(personInfoId, emailAddress.getEmailAddressId());

        displayRegisteredInfo(personInfo, passport, address, phoneNumber, emailAddress);
    }

    @Override
    public void displayRegisteredInfo(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    ) {
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
