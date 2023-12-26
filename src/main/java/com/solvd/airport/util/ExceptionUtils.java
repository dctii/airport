package com.solvd.airport.util;

import com.solvd.airport.exception.InvalidDecimalException;
import com.solvd.airport.exception.StringLengthException;

public class ExceptionUtils {
    public static void preventUtilityInstantiation() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }

    public static void preventConstantsInstantiation() {
        final String NO_CONSTANTS_INSTANTIATION_MESSAGE =
                "This is a constants class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_CONSTANTS_INSTANTIATION_MESSAGE);
    }

    public static void isStringLengthValid(String string, int maxLength) {
        if (string.length() > maxLength) {
            final String stringLengthMessage =
                    "String length is invalid. Cannot be more than " + maxLength + " characters";
            throw new StringLengthException(stringLengthMessage);
        }
    }

    public static void isDecimalValid(Number number, int precision, int scale) {
        String[] splitter = String.valueOf(number).split("\\.");
        int integerPlaces = splitter[0].length();
        int decimalPlaces = splitter.length > 1 ? splitter[1].length() : 0;

        if (integerPlaces + decimalPlaces > precision || decimalPlaces > scale) {
            final String invalidDecimalExceptionMsg =
                    "Invalid decimal: " + number + " for DECIMAL" + StringFormatters.nestInParentheses(precision + "," + scale);
            throw new InvalidDecimalException(invalidDecimalExceptionMsg);
        }
    }

    private ExceptionUtils() {
        preventUtilityInstantiation();
    }
}
