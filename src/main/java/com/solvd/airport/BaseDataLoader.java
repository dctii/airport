package com.solvd.airport;


import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.FilepathConstants;
import com.solvd.airport.util.LoadUtils;
import com.solvd.airport.util.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class BaseDataLoader {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BASE_DATA_LOADER);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            LOGGER.info(StringConstants.NEWLINE);

            LOGGER.info("{}{}=== Airport Base Data Loading Tools: ==={}",
                    AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
            LOGGER.info("[0] Exit");
            LOGGER.info("{}{}[1] Execute all 'Load' options{}", AnsiCodes.BLUE, AnsiCodes.BOLD, AnsiCodes.RESET_ALL);
            LOGGER.info("[2] Load Country ISO Codes and Country Names Data");
            LOGGER.info("[3] Load Timezones Data");
            LOGGER.info("[4] Load Airports Data");
            LOGGER.info("[5] Load Airlines Data");
            LOGGER.info("[6] Load Terminals Data");
            LOGGER.info("[7] Load Gates Data");
            LOGGER.info("[8] Load Bookings Data");

            LOGGER.info(StringConstants.NEWLINE + "Enter your choice: ");

            int choice = scanner.nextInt();
            LOGGER.info(StringConstants.NEWLINE);

            scanner.nextLine();

            switch (choice) {
                case 1:
                    LoadUtils.loadCountryData();
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadTimeZones();
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadLargeAirports(FilepathConstants.OUR_AIRPORTS_CSV_DATA_FILEPATH);
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadAirlines(FilepathConstants.AIRLINES_JSON);
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadTerminals(FilepathConstants.TERMINALS_XML);
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadGates(FilepathConstants.GATES_XML);
                    LOGGER.info(StringConstants.NEWLINE);
                    LoadUtils.loadBookings(FilepathConstants.BOOKINGS_JSON);
                    break;
                case 2:
                    LoadUtils.loadCountryData();
                    break;
                case 3:
                    LoadUtils.loadTimeZones();
                    break;
                case 4:
                    // 'airports.csv' (2024/01/07) from https://ourairports.com/data/
                    LoadUtils.loadLargeAirports(FilepathConstants.OUR_AIRPORTS_CSV_DATA_FILEPATH);
                    break;
                case 5:
                    LoadUtils.loadAirlines(FilepathConstants.AIRLINES_JSON);
                    break;
                case 6:
                    LoadUtils.loadTerminals(FilepathConstants.TERMINALS_XML);
                    break;
                case 7:
                    LoadUtils.loadGates(FilepathConstants.GATES_XML);
                    break;
                case 8:
                    LoadUtils.loadBookings(FilepathConstants.BOOKINGS_JSON);
                    break;
                case 0:
                    LOGGER.info("Exiting...");
                    System.exit(0);
                default:
                    LOGGER.info("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
