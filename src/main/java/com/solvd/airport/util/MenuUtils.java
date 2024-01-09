package com.solvd.airport.util;

import com.solvd.airport.domain.*;
import com.solvd.airport.persistence.*;
import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.impl.BoardPassengerServiceImpl;
import com.solvd.airport.service.impl.CheckInServiceImpl;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.*;

public class MenuUtils {
    private static final Logger LOGGER = LogManager.getLogger(MenuUtils.class);

    private static final CheckInService checkInService = new CheckInServiceImpl();
    private static final BoardPassengerService boardPassengerService = new BoardPassengerServiceImpl();
    private static final RegisterPassportHolderService registerPassportHolderService = new RegisterPassportHolderServiceImpl();


    public static void registerPassportHolder(Scanner scanner) {
        LOGGER.info("{}{}=== Register Passport Holder: ==={}",
                AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);



        // personal info
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

        // TODO: Need to check if exists in database already and reroute user to put in another option.

        // passport details
        String passportNumber = ScannerUtils.getAndCleanPassportNum(scanner, "Enter Passport Number:", String::toUpperCase);
        Date issueDate = ScannerUtils.getDateInput(scanner, "Enter Passport Issue Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);
        Date expiryDate = ScannerUtils.getDateInput(scanner, "Enter Passport Expiry Date (YYYY-MM-DD):", StringConstants.YEAR_FIRST_DATE_PATTERN);


        // address
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

        // phone number
        String phoneNumber = ScannerUtils.getAndCheckInput(scanner, "Enter Phone Number (include country code, e.g., +15551234567):",
                input -> input.startsWith(StringConstants.PLUS_SIGN));

        // email address
        String emailAddress = ScannerUtils.checkAndCleanInput(scanner, "Enter Email Address:",
                String::toLowerCase,
                BooleanUtils::isValidEmail
        );


        // create domain instances
        PersonInfo personInfo = new PersonInfo(surname, givenName, middleName, birthdate, sex);
        Passport passport = new Passport(passportNumber, issueDate, expiryDate);
        Address address = new Address(street, city, postalCode, countryCode);
        PhoneNumber phone = new PhoneNumber(phoneNumber);
        EmailAddress email = new EmailAddress(emailAddress);

        // call RegisterPassportHolderService function
        LOGGER.info("Registering passport holder info");
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

    public static void loadCountryData() {
        final CountryDAO countryDAO = DataAccessProvider.getCountryDAO();

        LOGGER.info(
                "{}Loading country data...{}",
                AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
        );
        Arrays.stream(Locale.getISOCountries()).forEach(code -> {
            String countryName = getCountryNameByIsoCode(code);
            if (countryDAO.doesCountryCodeExist(code)) {

                Country existingCountry = countryDAO.getCountryByCode(code);
                if (!existingCountry.getCountryName().equals(countryName)) {
                    LOGGER.info(
                            "{}'{}' already exists, overwriting country name with '{}'.{}",
                            AnsiCodes.YELLOW, existingCountry.getCountryName(), countryName, AnsiCodes.RESET_ALL
                    );
                    existingCountry.setCountryName(countryName);
                    countryDAO.updateCountry(existingCountry);
                } else {
                    LOGGER.info(
                            "{}'{}' already exists, skipping.{}",
                            AnsiCodes.YELLOW, existingCountry.getCountryName(), AnsiCodes.RESET_ALL
                    );
                }
            } else {
                Country newCountry = new Country();
                newCountry.setCountryCode(code);
                newCountry.setCountryName(countryName);

                countryDAO.createCountry(newCountry);
            }
        });
        LOGGER.info(
                "{}Country Data Loaded Successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    private static String getCountryNameByIsoCode(String code) {
        Locale locale = new Locale("", code);
        return locale.getDisplayCountry();
    }


    public static void loadTimeZones() {
        final TimezoneDAO timezoneDAO = DataAccessProvider.getTimezoneDAO();

        LOGGER.info(
                "{}Loading Time Zones...{}",
                AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
        );

        Arrays.stream(TimeZone.getAvailableIDs()).forEach(tzString -> {
            if (!timezoneDAO.doesTimezoneExist(tzString)) {
                Timezone timezone = new Timezone();
                timezone.setTimezone(tzString);
                timezoneDAO.createTimezone(timezone);
            } else {
                LOGGER.info(
                        "{}'{}' already exists, skipping.{}",
                        AnsiCodes.YELLOW, tzString, AnsiCodes.RESET_ALL
                );
            }
        });

        LOGGER.info(
                "{}Time Zones Loaded Successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadLargeAirports(String csvResourcePath) {
        CSVParser csvParser = null;
        final AirportDAO airportDAO = DataAccessProvider.getAirportDAO();

        LOGGER.info(
                "{}Loading 'large airports' IATA codes and Airport names into database...{}",
                AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
        );

        try {
            InputStream inputStream = MenuUtils.class.getClassLoader().getResourceAsStream(csvResourcePath);
            if (inputStream == null) {
                throw new FileNotFoundException("CSV File for airports not found in resources");
            }

            Reader reader = new InputStreamReader(
                    inputStream,
                    StandardCharsets.UTF_8
            );

            final String TYPE_HEADER_NAME = "type";
            final String LARGE_AIRPORT_TYPE = "large_airport";
            final String IATA_CODE_HEADER_NAME = "iata_code";
            final String AIRPORT_NAME_HEADER_NAME = "name";

            String[] csvHeaders = {
                    "id", "ident", TYPE_HEADER_NAME,
                    AIRPORT_NAME_HEADER_NAME, "latitude_deg",
                    "longitude_deg", "elevation_ft",
                    "continent", "iso_country",
                    "iso_region", "municipality",
                    "scheduled_service", "gps_code",
                    IATA_CODE_HEADER_NAME, "local_code",
                    "home_link", "wikipedia_link",
                    "keywords"
            };

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(csvHeaders)
                    .setSkipHeaderRecord(true)
                    .build();

            csvParser = new CSVParser(reader, csvFormat);

            for (CSVRecord csvRecord : csvParser) {
                String type = csvRecord.get(TYPE_HEADER_NAME);
                if (LARGE_AIRPORT_TYPE.equalsIgnoreCase(type)) {
                    String airportIataCode = csvRecord.get(IATA_CODE_HEADER_NAME);
                    String airportName = csvRecord.get(AIRPORT_NAME_HEADER_NAME);

                    if (!airportDAO.doesAirportExist(airportIataCode)) {
                        Airport airport = new Airport();
                        airport.setAirportCode(airportIataCode);
                        airport.setAirportName(airportName);

                        airportDAO.createAirportWithoutAddress(airport);
                    } else {
                        LOGGER.info(
                                "{}'{}' already exists, skipping.{}",
                                AnsiCodes.YELLOW, airportName, AnsiCodes.RESET_ALL
                        );
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error loading large airports: ", e);
        }

        LOGGER.info(
                "{}Loading airport data successful{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadAirlines(String jsonFilePath) {
        AirlineDAO airlineDAO = DataAccessProvider.getAirlineDAO();
        List<Airline> airlines = JacksonUtils.extractAirlines(jsonFilePath);
        for (Airline airline : airlines) {
            String airlineCode = airline.getAirlineCode();
            String airlineName = airline.getAirlineName();

            if (!airlineDAO.doesAirlineExist(airlineCode)) {
                airlineDAO.createAirlineWithoutAddress(airline);
            } else {
                LOGGER.info(
                        "{}'{}' already exists, skipping.{}",
                        AnsiCodes.YELLOW, airlineName, AnsiCodes.RESET_ALL
                );
            }
        }
        LOGGER.info(
                "{}Airlines data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadTerminals(String xmlFilePath) {
        TerminalDAO terminalDAO = DataAccessProvider.getTerminalDAO();
        List<Terminal> terminals = StaxUtils.extractTerminals(xmlFilePath);
        for (Terminal terminal : terminals) {
            String airportCode = terminal.getAirportCode();
            String terminalCode = terminal.getTerminalCode();

            if (!terminalDAO.doesTerminalExist(airportCode, terminalCode)) {
                terminalDAO.createTerminal(terminal);
            } else {
                LOGGER.info(
                        "{} Terminal '{}' at '{}' already exists, skipping.{}",
                        AnsiCodes.YELLOW,
                        terminalCode, airportCode,
                        AnsiCodes.RESET_ALL
                );
            }
        }
        LOGGER.info(
                "{}Terminals data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadGates(String xmlFilePath) {
        GateDAO gateDAO = DataAccessProvider.getGateDAO();
        GateCollection gateCollection = JaxbUtils.extractGates(xmlFilePath);

        for (Gate gate : gateCollection.getGates()) {
            String gateCode = gate.getGateCode();
            String airportCode = gate.getAirportCode();
            String terminalCode = gate.getTerminalCode();

            if (!gateDAO.doesGateExist(airportCode, terminalCode, gateCode)) {
                gateDAO.createGate(gate);
            } else {
                LOGGER.info(
                        "{} Gate '{}' at '{}' in '{}' already exists, skipping.{}",
                        AnsiCodes.YELLOW,
                        gateCode, airportCode, terminalCode,
                        AnsiCodes.RESET_ALL
                );
            }
        }
        LOGGER.info(
                "{}Gates data loaded successfully.{}",
                AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
        );
    }


    private MenuUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }

}
