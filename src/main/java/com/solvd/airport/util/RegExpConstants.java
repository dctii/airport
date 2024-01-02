package com.solvd.airport.util;

public class RegExpConstants {
    public static final String UPPERCASE_M_OR_F = "[MF]";
    public static final String LOWERCASE_Y_OR_N = "[yn]";
    public static final String DECIMAL_WITH_SCALE_OF_0_OR_2 = "\\d+(\\.\\d{1,2})?";

    private RegExpConstants() {
        ExceptionUtils.preventConstantsInstantiation();
    }
}
