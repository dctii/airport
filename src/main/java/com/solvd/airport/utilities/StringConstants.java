package com.solvd.airport.utilities;

public final class StringConstants {
    public static final char LOWER_CASE_M_CHAR = 'm';
    public static final String EMPTY_STRING = "";
    public static final String SINGLE_WHITESPACE = " ";
    public static final String SINGLE_QUOTATION = "'";
    public static final String OPENING_BRACKET = "[";
    public static final String OPENING_CURLY_BRACE = "{";
    public static final String CLOSING_CURLY_BRACE = "}";
    public static final String ESCAPE_SEQUENCE = "\033";
    public static final String CARRIAGE_RETURN = "\r";
    public static final String NEWLINE = "\n";
    public static final String EQUALS_OPERATOR = "=";
    public static final String PADDED_EQUALS_OPERATOR =
            SINGLE_WHITESPACE + EQUALS_OPERATOR + SINGLE_WHITESPACE;
    public static final String COMMA_DELIMITER = ", ";
    public static final String COLON_DELIMITER = ": ";
    public static final String NULL_STRING = "null";
    public static final String ADD_STRING = "add";
    public static final String SUBTRACT_STRING = "subtract";
    public static final String MULTIPLY_STRING = "multiply";
    public static final String DIVIDE_STRING = "divide";
    public static final String DASH_STRING = "-";
    public static final String SCREAMING_THREAD_STRING = "THREAD";


    private StringConstants() {
        final String NO_CONSTANTS_INSTANTIATION_MESSAGE =
                "This is a constants class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_CONSTANTS_INSTANTIATION_MESSAGE);
    }
}
