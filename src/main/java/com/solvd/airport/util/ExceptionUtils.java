package com.solvd.airport.util;

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

    private ExceptionUtils() {
        preventUtilityInstantiation();
    }
}
