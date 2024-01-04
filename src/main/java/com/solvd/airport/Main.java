package com.solvd.airport;

import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.MenuUtils;
import com.solvd.airport.util.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/*
NOTES:
    To set up from inside mysql console:

        Need to start from a specific point in the DB.
        DROP DATABASE airport;

        CREATE DATABASE airport;
        USE airport;

            # will create schema for db
        source /path/to/airport_schema.sql
            # will set up data to the current point to run CheckInServiceImpl.performCheckIn
        source /path/to/load_base_data.sql
*/

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            LOGGER.info(StringConstants.NEWLINE);

            LOGGER.info("{}{}=== Airport Check-in System: ==={}",
                    AnsiCodes.BOLD, AnsiCodes.YELLOW, AnsiCodes.RESET_ALL);
            LOGGER.info("[0] Exit");
            LOGGER.info("[1] Register Passport Holder");
            LOGGER.info("[2] Perform Check-In");
            LOGGER.info("[3] Board Passengers");

            LOGGER.info(StringConstants.NEWLINE + "Enter your choice: ");

            int choice = scanner.nextInt();
            LOGGER.info(StringConstants.NEWLINE);

            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Testable as long as schemas are established for db
                    MenuUtils.registerPassportHolder(scanner);
                    break;
                case 2:
                    // Test with "emi_sato@air-japan.co.jp";
                    // Test with KAYAK654321;
                    MenuUtils.performCheckIn(scanner);
                    break;
                case 3:
                    // Test with KAYAK654321 after checking in with it;
                    MenuUtils.boardPassenger(scanner);
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
