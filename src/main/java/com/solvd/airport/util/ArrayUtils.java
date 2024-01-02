package com.solvd.airport.util;

public class ArrayUtils {

    public static int[] intArrayOf(int... items) {
        return items;
    }


    private ArrayUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
