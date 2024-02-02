package com.solvd.airport.util;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.Airline;
import com.solvd.airport.domain.Airport;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.Country;
import com.solvd.airport.domain.GateCollection;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.domain.Timezone;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.persistence.AirlineDAO;
import com.solvd.airport.persistence.AirportDAO;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CountryDAO;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.persistence.TerminalDAO;
import com.solvd.airport.persistence.TimezoneDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.StreamSupport;

public class LoadUtils {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.LOAD_UTILS);

    /* TODO: add loader for
        email_addresses
        phone_numbers
        addresses
        person_info
        flights
        airline_staff
        flight_staff
        passports
        bookings
     */

    public static void loadCountryData() {
        final CountryDAO countryDAO = DataAccessProvider.getCountryDAO();

        LOGGER.info(
                "{}Loading country data...{}",
                AnsiCodes.YELLOW, AnsiCodes.RESET_ALL
        );
        Arrays.stream(Locale.getISOCountries()).forEach(code -> {
            String countryName = getCountryNameByIsoCode(code).trim();
            if (countryDAO.doesCountryCodeExist(code)) {

                Country existingCountry = countryDAO.getCountryByCode(code);
                if (!existingCountry.getCountryName().equals(countryName)) {
                    LOGGER.info(
                            "{}'{}' already exists, overwriting country name with '{}'.{}",
                            AnsiCodes.YELLOW, existingCountry.getCountryName(), countryName, AnsiCodes.RESET_ALL
                    );
                    existingCountry.setCountryName(countryName);
                    countryDAO.update(existingCountry);
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

                countryDAO.create(newCountry);
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
                timezoneDAO.create(timezone);
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
            InputStream inputStream = ClassConstants.MENU_UTILS.getClassLoader().getResourceAsStream(csvResourcePath);
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

            StreamSupport.stream(csvParser.spliterator(), false)
                    .filter(csvRecord -> LARGE_AIRPORT_TYPE.equalsIgnoreCase(csvRecord.get(TYPE_HEADER_NAME)))
                    .forEach(csvRecord -> {
                        String airportIataCode = csvRecord.get(IATA_CODE_HEADER_NAME);
                        String airportName = csvRecord.get(AIRPORT_NAME_HEADER_NAME);

                        if (!airportDAO.doesAirportExist(airportIataCode)) {
                            Airport airport = new Airport();
                            airport.setAirportCode(airportIataCode);
                            airport.setAirportName(airportName);

                            airportDAO.create(airport);
                        } else {
                            LOGGER.info(
                                    "{}'{}' already exists, skipping.{}",
                                    AnsiCodes.YELLOW, airportName, AnsiCodes.RESET_ALL
                            );
                        }
                    });

        } catch (FileNotFoundException e) {
            LOGGER.error("CSV File not found: ", e);
        } catch (IOException e) {
            LOGGER.error("Error reading the CSV File: ", e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid data format in CSV File: ", e);
        }

        LOGGER.info(
                "{}Loading airport data successful{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadAirlines(String jsonFilePath) {
        AirlineDAO airlineDAO = DataAccessProvider.getAirlineDAO();
        AddressDAO addressDAO = DataAccessProvider.getAddressDAO();


        List<Airline> airlines = JacksonUtils.extractAirlines(jsonFilePath);
        airlines.forEach(airline -> {
            createOrGetAddressForAirline(airline, addressDAO);
            createAirlineIfNotExists(airline, airlineDAO);
        });
        LOGGER.info(
                "{}Airlines data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    private static void createAirlineIfNotExists(Airline airline, AirlineDAO airlineDAO) {
        if (!airlineDAO.doesAirlineExist(airline.getAirlineCode())) {
            airlineDAO.create(airline);
        } else {
            LOGGER.info(
                    "{}'{}' already exists, skipping.{}",
                    AnsiCodes.YELLOW, airline.getAirlineName(), AnsiCodes.RESET_ALL
            );
        }
    }

    private static void createOrGetAddressForAirline(Airline airline, AddressDAO addressDAO) {
        Address address = airline.getAddress();
        if (address != null) {
            Address existingAddress = addressDAO.getAddressByUniqueFields(
                    address.getStreet(), address.getCity(),
                    address.getPostalCode(), address.getCountryCode()
            );
            if (existingAddress == null) {
                int newAddressId = addressDAO.create(address);
                address.setAddressId(newAddressId);
                LOGGER.info(
                        "{}New address created with ID: {}{}",
                        AnsiCodes.GREEN, newAddressId, AnsiCodes.RESET_ALL
                );
            } else {
                airline.setAddress(existingAddress);
                LOGGER.info(
                        "{}Existing address used with ID: {}{}",
                        AnsiCodes.BLUE, existingAddress.getAddressId(), AnsiCodes.RESET_ALL
                );
            }
        }
    }

    public static void loadTerminals(String xmlFilePath) {
        TerminalDAO terminalDAO = DataAccessProvider.getTerminalDAO();
        List<Terminal> terminals = StaxUtils.extractTerminals(xmlFilePath);
        terminals.stream()
                .filter(terminal -> !terminalDAO.doesTerminalExist(
                        terminal.getAirportCode(),
                        terminal.getTerminalCode()
                ))
                .forEach(terminalDAO::create);

        terminals.stream()
                .filter(terminal -> terminalDAO.doesTerminalExist(
                        terminal.getAirportCode(),
                        terminal.getTerminalCode()
                ))
                .forEach(terminal -> {
                    LOGGER.info(
                            "{} Terminal '{}' at '{}' already exists, skipping.{}",
                            AnsiCodes.YELLOW,
                            terminal.getTerminalCode(), terminal.getAirportCode(),
                            AnsiCodes.RESET_ALL
                    );
                });
        LOGGER.info(
                "{}Terminals data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadGates(String xmlFilePath) {
        GateDAO gateDAO = DataAccessProvider.getGateDAO();
        GateCollection gateCollection = JaxbUtils.extractGates(xmlFilePath);

        gateCollection.getGates().stream()
                .filter(gate -> !gateDAO.doesGateExist(
                        gate.getGateCode(), gate.getAirportCode()
                ))
                .forEach(gateDAO::create);

        gateCollection.getGates().stream()
                .filter(gate -> gateDAO.doesGateExist(
                        gate.getGateCode(), gate.getAirportCode()
                ))
                .forEach(gate -> {
                    LOGGER.info(
                            "{} Gate '{}' at '{}' in '{}' already exists, skipping.{}",
                            AnsiCodes.YELLOW,
                            gate.getGateCode(), gate.getAirportCode(), gate.getTerminalCode(),
                            AnsiCodes.RESET_ALL
                    );
                });

        LOGGER.info(
                "{}Gates data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    public static void loadBookings(String jsonFilePath) {
        BookingDAO bookingDAO = DataAccessProvider.getBookingDAO();

        List<Booking> bookings = JacksonUtils.extractBookings(jsonFilePath);
        bookings.forEach(booking -> {
            createBookingIfNotExists(booking, bookingDAO);
        });
        LOGGER.info(
                "{}Bookings data loaded successfully.{}",
                AnsiCodes.GREEN, AnsiCodes.RESET_ALL
        );
    }

    private static void createBookingIfNotExists(Booking booking, BookingDAO bookingDAO) {
        if (!bookingDAO.doesBookingExist(booking.getBookingNumber())) {
            bookingDAO.create(booking);
        } else {
            LOGGER.info(
                    "{}'{}' already exists, skipping.{}",
                    AnsiCodes.YELLOW, booking.getBookingNumber(), AnsiCodes.RESET_ALL
            );
        }
    }


    private LoadUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
