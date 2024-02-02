package com.solvd.airport.util;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.UpdateFlightGateService;
import com.solvd.airport.service.impl.BoardPassengerServiceImpl;
import com.solvd.airport.service.impl.CheckInServiceImpl;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import com.solvd.airport.service.impl.UpdateFlightGateServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MenuUtils {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.MENU_UTILS);

    private static final CheckInService checkInService = new CheckInServiceImpl();
    private static final BoardPassengerService boardPassengerService = new BoardPassengerServiceImpl();
    private static final RegisterPassportHolderService registerPassportHolderService = new RegisterPassportHolderServiceImpl();

    private static final UpdateFlightGateService updateFlightGateService = new UpdateFlightGateServiceImpl();
    private static final BookingDAO bookingDAO = DataAccessProvider.getBookingDAO();


    public static void registerPassportHolder(Scanner scanner) {
        LOGGER.info("{}{}=== Register Passport Holder: ==={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);

        PersonInfo personInfo = getPersonInfo(scanner);
        Passport passport = getPassport(scanner);
        Address address = getAddress(scanner);
        PhoneNumber phone = getPhoneNumber(scanner);
        EmailAddress email = getEmailAddress(scanner);

        // call RegisterPassportHolderService function
        LOGGER.info("Registering passport holder info");
        registerPassportHolderService.registerPassportHolder(
                personInfo,
                passport,
                address,
                phone,
                email
        );

        LOGGER.info("{}Registration completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
    }

    private static EmailAddress getEmailAddress(Scanner scanner) {
        String emailAddress = ScannerUtils.checkAndCleanInput(scanner, "Enter Email Address (e.g., 'example@domain.com'):",
                String::toLowerCase,
                BooleanUtils::isValidEmail
        );

        EmailAddress email = new EmailAddress(emailAddress);
        return email;
    }

    public static EmailAddress getEmailAddress(String input) {
        String emailAddress = ScannerUtils.checkAndCleanInput(
                input,
                String::toLowerCase,
                BooleanUtils::isValidEmail
        );

        return new EmailAddress(emailAddress);
    }


    private static PhoneNumber getPhoneNumber(Scanner scanner) {
        String phoneNumber = ScannerUtils.getAndCheckInput(
                scanner,
                "Enter Phone Number (include country code, e.g., +15551234567):",
                input -> input.startsWith(StringConstants.PLUS_SIGN)
        );

        return new PhoneNumber(phoneNumber);
    }

    public static PhoneNumber getPhoneNumber(String input) {
        String phoneNumberString = ScannerUtils.getAndCheckInput(input,
                stringInput -> stringInput.startsWith(StringConstants.PLUS_SIGN));

        return new PhoneNumber(phoneNumberString);
    }

    private static Address getAddress(Scanner scanner) {
        String street = ScannerUtils.getAndCleanInput(scanner, "Enter Street Address:", String::toUpperCase);
        String city = ScannerUtils.getAndCleanInput(scanner, "Enter City:", String::toUpperCase);
        String postalCode = ScannerUtils.getInput(scanner, "Enter Postal Code");
        String countryCode = ScannerUtils.checkAndCleanCountryCode(scanner, "Enter Country Code (e.g., US, JP):",
                String::toUpperCase,
                input -> {
                    // Sets input to uppercase and checks if it matches the list of ISO country codes
                    String upperCaseInput = input.toUpperCase();
                    return input.length() == 2
                            && Arrays.asList(Locale.getISOCountries()).contains(upperCaseInput);
                });

        return new Address(street, city, postalCode, countryCode);
    }

    public static Address getAddress(String streetString, String cityString, String postalCodeString, String countryCodeString) {
        String street = ScannerUtils.getAndCleanInput(streetString, String::toUpperCase);
        String city = ScannerUtils.getAndCleanInput(cityString, String::toUpperCase);
        String postalCode = ScannerUtils.getAndCleanInput(postalCodeString, String::toUpperCase);
        String countryCode =
                ScannerUtils.checkAndCleanCountryCode(
                        countryCodeString,
                        String::toUpperCase,
                        input -> {
                            // Sets input to uppercase and checks if it matches the list of ISO country codes
                            String upperCaseInput = input.toUpperCase();
                            return input.length() == 2
                                    && Arrays.asList(Locale.getISOCountries()).contains(upperCaseInput);
                        });

        return new Address(street, city, postalCode, countryCode);
    }

    private static Passport getPassport(Scanner scanner) {
        String passportNumber = ScannerUtils.getAndCleanInput(scanner, "Enter Passport Number:", String::toUpperCase);
        Date issueDate = ScannerUtils.getDateInput(scanner, "Enter Passport Issue Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);
        Date expiryDate = ScannerUtils.getDateInput(scanner, "Enter Passport Expiry Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);

        return new Passport(passportNumber, issueDate, expiryDate);
    }

    public static Passport getPassport(String passportNumString, String issueDateString, String expiryDateString) {
        String passportNumber = ScannerUtils.getAndCleanInput(passportNumString, String::toUpperCase);
        Date issueDate = ScannerUtils.getDateInput(issueDateString, StringConstants.YEAR_FIRST_DATE_PATTERN);
        Date expiryDate = ScannerUtils.getDateInput(expiryDateString, StringConstants.YEAR_FIRST_DATE_PATTERN);

        return new Passport(passportNumber, issueDate, expiryDate);
    }

    private static PersonInfo getPersonInfo(Scanner scanner) {
        String surname = ScannerUtils.getAndCleanInput(scanner, "Enter Surname:", String::toUpperCase);
        String givenName = ScannerUtils.getAndCleanInput(scanner, "Enter Given Name:", String::toUpperCase);
        String middleName = ScannerUtils.getAndCleanInput(scanner, "Enter Middle Name (or type 'none' if not applicable):", String::toUpperCase);
        if ("none".equalsIgnoreCase(middleName) || "".equals(middleName)) {
            middleName = null;
        }
        Date birthdate = ScannerUtils.getDateInput(scanner, "Enter Birthdate (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);
        String sex = ScannerUtils.checkAndCleanInput(scanner, "Enter Sex (M/F):",
                String::toUpperCase,
                input -> input.matches(RegExpConstants.UPPERCASE_M_OR_F));

        return new PersonInfo(surname, givenName, middleName, birthdate, sex);
    }

    public static PersonInfo getPersonInfo(String surnameString, String givenNameString, String middleNameString, String birthdateString, String sexString) {
        String surname = ScannerUtils.getAndCleanInput(surnameString, String::toUpperCase);
        String givenName = ScannerUtils.getAndCleanInput(givenNameString, String::toUpperCase);
        String middleName = ScannerUtils.getAndCleanInput(middleNameString, String::toUpperCase);
        if ("none".equalsIgnoreCase(middleName) || "".equals(middleName)) {
            middleName = null;
        }
        Date birthdate = ScannerUtils.getDateInput(birthdateString, StringConstants.YEAR_FIRST_DATE_PATTERN);
        String sex = ScannerUtils.checkAndCleanInput(
                sexString,
                String::toUpperCase,
                input -> input.matches(RegExpConstants.UPPERCASE_M_OR_F)
        );

        return new PersonInfo(surname, givenName, middleName, birthdate, sex);
    }

    public static void performCheckIn(Scanner scanner) {
        String staffEmail = ScannerUtils.checkAndCleanInput(scanner, "Enter Airline Staff Email (i.e. 'paul_blart@delta.com' or 'emi_sato@air-japan.co.jp':",
                String::toLowerCase,
                BooleanUtils::isValidEmail);

        // TODO: need to ask to try again if the booking number doesn't exist
        String bookingNumber = ScannerUtils.getAndCleanInput(scanner, "Enter Booking Number (i.e. EXPEDIA001, KAYAK001, KAYAK002):", String::toUpperCase);

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
        checkInService.performCheckIn(staffEmail, bookingNumber, hasBaggage, weight);
        LOGGER.info("{}Check-In completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
    }

    public static void boardPassenger(Scanner scanner) {
        LOGGER.info("=== Board Passenger onto Plane ===");

        String bookingNumber = ScannerUtils.getAndCleanInput(scanner,
                "Enter Booking Number for Boarding (i.e. EXPEDIA001, KAYAK001, KAYAK002--must be checked-in first):",
                String::toUpperCase
        );

        Booking currentBooking = bookingDAO.getBookingByBookingNumber(bookingNumber);

        boolean wasBoardingSuccessful = boardPassengerService.boardPassenger(bookingNumber);

        if (wasBoardingSuccessful) {
            LOGGER.info(
                    "{}Boarding completed successfully.{}\n",
                    AnsiCodes.GREEN,
                    AnsiCodes.RESET_ALL
            );
        } else if (!bookingDAO.doesBookingExist(bookingNumber)) {
            LOGGER.info(
                    "{}Booking does not exist.{}\n",
                    AnsiCodes.RED,
                    AnsiCodes.RESET_ALL
            );
        } else if (
                !currentBooking
                        .getStatus().equalsIgnoreCase(BookingDAO.STATUS_CHECKED_IN)
        ) {
            LOGGER.info(
                    "{}Boarding unsuccessful because booking's status is not 'Checked-in' .{}\n",
                    AnsiCodes.YELLOW,
                    AnsiCodes.RESET_ALL
            );
        } else {
            LOGGER.info(
                    "{}Boarding unsuccessful due to an invalid association with the inserted Booking Number. Try again.{}\n",
                    AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
            );
        }
    }

    public static void updateFlightGate(Scanner scanner) {
        LOGGER.info("=== Update Departure Gate for Flight ===");

        String flightCode = ScannerUtils.checkAndCleanInput(scanner,
                "Enter Flight Code:",
                String::toUpperCase,
                SQLUtils::doesFlightExist
        );

        String airportCode = ScannerUtils.checkAndCleanInput(scanner,
                "Enter Airport Code (i.e. LAX):",
                String::toUpperCase,
                SQLUtils::doesAirportExist
        );

        String gateCode = ScannerUtils.checkAndCleanInput(scanner,
                "Enter Gate Code (i.e. 130-159, 201A, 201B, 202-208, 209A-209B, 210A-210B, 221, 225):",
                String::toUpperCase,
                input -> SQLUtils.doesGateExist(input, airportCode)
        );

        Gate newGate = new Gate(gateCode, airportCode);

        // uses JAXB
        updateFlightGateService.updateFlightGate(flightCode, newGate);


    }

    private MenuUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }

}
