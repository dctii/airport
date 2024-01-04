package com.solvd.airport.util;

import com.solvd.airport.domain.*;
import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.impl.BoardPassengerServiceImpl;
import com.solvd.airport.service.impl.CheckInServiceImpl;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Arrays;
import java.util.Scanner;

public class MenuUtils {
    private static final Logger LOGGER = LogManager.getLogger(MenuUtils.class);

    private static final CheckInService checkInService = new CheckInServiceImpl();
    private static final BoardPassengerService boardPassengerService = new BoardPassengerServiceImpl();
    private static final RegisterPassportHolderService registerPassportHolderService = new RegisterPassportHolderServiceImpl();


    public static void registerPassportHolder(Scanner scanner) {
        LOGGER.info("{}{}=== Register Passport Holder: ==={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);

        // personal info
        String surname = ScannerUtils.getInput(scanner, "Enter Surname:");
        String givenName = ScannerUtils.getInput(scanner, "Enter Given Name:");
        String middleName = ScannerUtils.getInput(scanner, "Enter Middle Name (or type 'none' if not applicable):");
        if ("none".equalsIgnoreCase(middleName) || "".equals(middleName)) {
            middleName = null;
        }
        Date birthdate = ScannerUtils.getDateInput(scanner, "Enter Birthdate (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);
        String sex = ScannerUtils.checkAndCleanInput(scanner, "Enter Sex (M/F):",
                String::toUpperCase,
                input -> input.matches(RegExpConstants.UPPERCASE_M_OR_F));

        // TODO: Need to check if exists in database already and reroute user to put in another option.

        // passport details
        String passportNumber = ScannerUtils.getAndCleanPassportNum(scanner, "Enter Passport Number:", String::toUpperCase);
        Date issueDate = ScannerUtils.getDateInput(scanner, "Enter Passport Issue Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);
        Date expiryDate = ScannerUtils.getDateInput(scanner, "Enter Passport Expiry Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);


        // address
        String street = ScannerUtils.getInput(scanner, "Enter Street Address:");

        String city = ScannerUtils.getAndCleanInput(scanner, "Enter City:", StringUtils::capitalize);

        String postalCode = ScannerUtils.getInput(scanner, "Enter Postal Code");

        String countryCode = ScannerUtils.checkAndCleanCountryCode(scanner, "Enter Country Code (e.g., US, JP):",
                String::toUpperCase,
                input -> {
                    String upperCaseInput = input.toUpperCase();
                    return input.length() == 2
                            && Arrays
                            .stream(Countries.values())
                            .anyMatch(country -> country.getCountryCode()
                                    .equals(upperCaseInput));
                });

        // phone number
        String phoneNumber = ScannerUtils.getAndCheckInput(scanner, "Enter Phone Number (include country code, e.g., +15551234567):",
                input -> input.startsWith(StringConstants.PLUS_SIGN));

        // email address
        String emailAddress = ScannerUtils.checkAndCleanInput(scanner, "Enter Email Address:",
                String::toLowerCase,
                BooleanUtils::isValidEmail);


        // create domain instances
        PersonInfo personInfo = new PersonInfo(surname, givenName, middleName, birthdate, sex);
        Passport passport = new Passport(passportNumber, issueDate, expiryDate);
        Address address = new Address(street, city, postalCode, countryCode);
        PhoneNumber phone = new PhoneNumber(phoneNumber);
        EmailAddress email = new EmailAddress(emailAddress);

        // call RegisterPassportHolderService function
        registerPassportHolderService.registerPassportHolder(personInfo, passport, address, phone, email);

        LOGGER.info("{}Registration completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
    }

    public static void performCheckIn(Scanner scanner) {
        // Test with "emi_sato@air-japan.co.jp";
        String staffEmail = ScannerUtils.checkAndCleanStaffEmail(scanner, "Enter Airline Staff Email:",
                String::toLowerCase,
                BooleanUtils::isValidEmail); // TODO: add something with the validator to see if the user's email is in the db as airline staff

        // Test with KAYAK654321;
        String bookingNumber = ScannerUtils.getAndCleanBookingNum(scanner, "Enter Booking Number:", String::toUpperCase);

        String hasBaggageString = ScannerUtils.checkAndCleanInput(scanner, "Does the passenger have baggage? Y/N",
                String::toLowerCase,
                input -> input.matches(RegExpConstants.LOWERCASE_Y_OR_N)
        );
        boolean hasBaggage = hasBaggageString.equalsIgnoreCase("y");
        double weight = 0.00;

        if (hasBaggage) {
            weight = Double.parseDouble(
                    ScannerUtils.getAndCheckInput(scanner,
                            "How much does the baggage weigh? Type in the weight (e.g., 23 or 23.00)",
                            input -> input.matches(RegExpConstants.DECIMAL_WITH_SCALE_OF_0_OR_2)
                    )
            );
        }

        try {
            checkInService.performCheckIn(staffEmail, bookingNumber, hasBaggage, weight);
            LOGGER.info("{}Check-In completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
        } catch (Exception e) {
            LOGGER.error("Error during check-in process: ", e);
        }
    }

    public static void boardPassenger(Scanner scanner) {
        LOGGER.info("=== Board Passenger onto Plane ===");

        String bookingNumber = ScannerUtils.getAndCleanInput(scanner,
                "Enter Booking Number for Boarding:",
                String::toUpperCase
        );

        try {
            boardPassengerService.boardPassenger(bookingNumber);
            LOGGER.info("{}Boarding completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
        } catch (Exception e) {
            LOGGER.error("Error during boarding process: ", e);
        }
    }

    private MenuUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }

}
