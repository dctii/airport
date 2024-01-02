package com.solvd.airport;

import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.MenuUtils;
import com.solvd.airport.util.StringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/*
NOTES:
    Need to start from a specific point in the DB.
    DROP DATABASE airport;

    CREATE DATABASE airport;
    USE airport;

        # will create schema for db
    source /path/to/airport_schema.sql
        # will set up data to the current point to run CheckInServiceImpl.performCheckIn
    source /path/to/airport_script.sql
*/

// TODO: Remove logs from doesCountryExist check
// TODO: add exist checks to passport, staff email, and booking number -- need to update DAOs
// TODO: make constants out of repetitive strings, create constants out of the table column names and table names
// TODO: create SQL query builder, use constants in SQLConstants to help with it
// TODO: create database loader that can be run via Java
// TODO: refactor to abstract away what can be abstracted away

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
                    MenuUtils.registerPassportHolder(scanner);
                    break;
                case 2:
                    MenuUtils.performCheckIn(scanner);
                    break;
                case 3:
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
