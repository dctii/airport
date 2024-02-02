package com.solvd.airport.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ScannerUtils {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.SCANNER_UTILS);


    public static String getInput(Scanner scanner, String prompt) {
        String input;
        LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
        input = scanner.nextLine();
        return getInput(input);
    }

    public static String getInput(String input) {
        return input;
    }

    public static String getAndCleanInput(
            Scanner scanner,
            String prompt,
            UnaryOperator<String> formatter
    ) {
        String input;
        LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
        input = scanner.nextLine();

        return getAndCleanInput(input, formatter);
    }

    public static String getAndCleanInput(
            String input,
            UnaryOperator<String> formatter
    ) {
        if (formatter != null) {
            input = formatter.apply(input);
        }
        return input;
    }


    public static String getAndCheckInput(
            Scanner scanner,
            String prompt,
            Predicate<String> validator
    ) {
        String input;

        do {
            LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
            input = scanner.nextLine();
        } while (validator != null && !validator.test(input));

        return input;
    }

    public static String getAndCheckInput(
            String input,
            Predicate<String> validator
    ) {
        if (validator != null && !validator.test(input)) {
            LOGGER.error("Data doesn't match validator expectations");
            throw new IllegalArgumentException("Data doesn't match validator expectations");
        }
        return input;
    }


    public static String checkAndCleanInput(
            Scanner scanner,
            String prompt,
            UnaryOperator<String> formatter,
            Predicate<String> validator
    ) {
        String input;
        do {
            LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
            input = scanner.nextLine();
            if (formatter != null) {
                input = formatter.apply(input);
            }
        } while (validator != null && !validator.test(input));
        return input;
    }

    public static String checkAndCleanInput(
            String input,
            UnaryOperator<String> formatter,
            Predicate<String> validator
    ) {

        if (formatter != null) {
            input = formatter.apply(input);
        }
        if (validator != null && !validator.test(input)) {
            throw new IllegalArgumentException("Input validation failed");
        }
        return input;
    }

    public static String checkAndCleanCountryCode(
            Scanner scanner,
            String prompt,
            UnaryOperator<String> formatter,
            Predicate<String> stringValidator
    ) {
        String input;
        boolean isValid;
        do {
            LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
            input = scanner.nextLine();
            if (formatter != null) {
                input = formatter.apply(input);
            }
            isValid = stringValidator != null
                    && stringValidator.test(input)
                    && SQLUtils.doesCountryCodeExist(input);
            if (!isValid) {
                LOGGER.info("Invalid country code. Please try again.");
            }
        } while (!isValid);
        return input;
    }

    public static String checkAndCleanCountryCode(
            String input,
            UnaryOperator<String> formatter,
            Predicate<String> stringValidator
    ) {
        input = formatter != null ? formatter.apply(input) : input;

        if (stringValidator != null && !stringValidator.test(input)) {
            LOGGER.info("Invalid country code. Please try again.");
            throw new IllegalArgumentException("Invalid country code: " + input);
        }
        return input;
    }

    public static Date getDateInput(Scanner scanner, String prompt, String datePattern) {
        String input;
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        while (true) {
            try {
                LOGGER.info("{}" + prompt + "{}", AnsiCodes.BLUE, AnsiCodes.RESET_ALL);
                input = scanner.nextLine();
                date = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                LOGGER.error("Invalid date format or invalid date. Please enter a date in YYYY-MM-DD format.");
            }
        }
        /*
            "Converting Between LocalDate and SQL Date"
            https://www.baeldung.com/java-convert-localdate-sql-date
        */
        return java.sql.Date.valueOf(date);
    }

    public static Date getDateInput(String input, String datePattern) {

        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        try {
            date = LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            LOGGER.error("Invalid date format or invalid date. Please enter a date in YYYY-MM-DD format.");
            throw e;
        }
        return java.sql.Date.valueOf(date);
    }

    private ScannerUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
