package com.solvd.airport;

import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.service.RegisterPassportHolderService;
import com.solvd.airport.service.impl.BoardPassengerServiceImpl;
import com.solvd.airport.service.impl.CheckInServiceImpl;
import com.solvd.airport.service.impl.RegisterPassportHolderServiceImpl;
import com.solvd.airport.util.AnsiCodes;
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


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final CheckInService checkInService = new CheckInServiceImpl();
    private static final BoardPassengerService boardPassengerService = new BoardPassengerServiceImpl();
    private static final RegisterPassportHolderService registerPassportHolderService = new RegisterPassportHolderServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            LOGGER.info("=== Airport Check-In System ===");
            LOGGER.info("[0] Exit");
            LOGGER.info("[1] Perform Check-In");
            LOGGER.info("[3] Board Passengers");
            LOGGER.info("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    performCheckIn(scanner);
                    break;
                case 2:
                    boardPassenger(scanner);
                case 0:
                    LOGGER.info("Exiting...");
                    System.exit(0);
                default:
                    LOGGER.info("Invalid choice. Please try again.");
            }
        }
    }

    private static void performCheckIn(Scanner scanner) {
        // Test with "emi_sato@air-japan.co.jp";
        LOGGER.info("Enter Airline Staff Email:");
        String staffEmail = scanner.next();

        // Test with KAYAK654321;
        LOGGER.info("Enter Booking Number:");
        String bookingNumber = scanner.next();

        LOGGER.info("Does the passenger have baggage? y/n");
        String hasBaggageString = scanner.next();
        boolean hasBaggage = hasBaggageString.equalsIgnoreCase("y");
        double weight = 0.00;

        if (hasBaggage) {
            LOGGER.info("How much does the baggage weigh? Type in the weight (e.g., 23.00)");
            String weightString = scanner.next();
            weight = Double.parseDouble(weightString);
        }

        try {
            // TODO: Gets stuck here
            checkInService.performCheckIn(staffEmail, bookingNumber, hasBaggage, weight);
            LOGGER.info("{}Check-In completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
        } catch (Exception e) {
            LOGGER.error("Error during check-in process: ", e);
        }
    }

    private static void boardPassenger(Scanner scanner) {
        LOGGER.info("Enter Booking Number for Boarding:");
        String bookingNumber = scanner.next();

        try {
            boardPassengerService.boardPassenger(bookingNumber);
            LOGGER.info("{}Boarding completed successfully.{}\n", AnsiCodes.GREEN, AnsiCodes.RESET_ALL);
        } catch (Exception e) {
            LOGGER.error("Error during boarding process: ", e);
        }
    }
}
