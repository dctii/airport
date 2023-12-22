package com.solvd.airport;

import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.service.impl.CheckInServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/*
NOTES:
    Need to start from a specific point in the DB.
    CREATE DATABASE airport;
    USE airport;
        # will create schema for db
    source /path/to/airport_schema.sql
        # will set up data to the current point to run CheckInServiceImpl.performCheckIn
    source /path/to/airport_script.sql
*/


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final CheckInService checkInService = new CheckInServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            LOGGER.info("--- Airport Check-In System ---");
            LOGGER.info("1. Perform Check-In");
            LOGGER.info("2. Exit");
            LOGGER.info("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    performCheckIn(scanner);
                    break;
                case 2:
                    LOGGER.info("Exiting...");
                    System.exit(0);
                default:
                    LOGGER.info("Invalid choice. Please try again.");
            }
        }
    }

    private static void performCheckIn(Scanner scanner) {
        LOGGER.info("Enter Booking Number:");
        String bookingNumber = scanner.next();

        LOGGER.info("Enter Flight Code:");
        String flightCode = scanner.next();

        try {
            CheckIn checkIn = new CheckIn();

            // TODO: Gets stuck here
            checkInService.performCheckIn(bookingNumber, flightCode);
            LOGGER.info("Check-In completed successfully.");
        } catch (Exception e) {
            LOGGER.error("Error during check-in process: ", e);
        }
    }
}
