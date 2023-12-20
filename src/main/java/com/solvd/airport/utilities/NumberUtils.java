package com.solvd.airport.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    public static int roundToInt(Number number) {
        if (number instanceof Float) {
            return Math.round((float) number);
        } else if (number instanceof Double) {
            return (int) Math.round((double) number);
        } else if (number instanceof BigDecimal) {
            /*
                "Java BigDecimal: Round to the nearest whole value"
                https://stackoverflow.com/questions/4134047/java-bigdecimal-round-to-the-nearest-whole-value
            */
            return ((BigDecimal) number)
                    .setScale(0, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP)
                    .intValue();
        } else if (number instanceof Integer) {
            return number.intValue();
        } else {
            throw new IllegalArgumentException("Number type not supported");
        }
    }

    public static BigDecimal ensureBigDecimal(Number value) {
        BigDecimal bigDecimalValue;
        if (value instanceof BigDecimal) {
            bigDecimalValue = (BigDecimal) value;
        } else {
            bigDecimalValue = BigDecimal.valueOf(value.doubleValue());
        }
        return bigDecimalValue;
    }


    private NumberUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
