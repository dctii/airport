package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class Timezone {
    private String timezone;

    final static private int TIMEZONE_MAX_WIDTH = 45;

    public Timezone() {
    }

    public Timezone(String timezone) {
        ExceptionUtils.isStringLengthValid(timezone, TIMEZONE_MAX_WIDTH);
        ExceptionUtils.isValidTimeZone(timezone);

        this.timezone = timezone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        ExceptionUtils.isStringLengthValid(timezone, TIMEZONE_MAX_WIDTH);
        ExceptionUtils.isValidTimeZone(timezone);

        this.timezone = timezone;
    }

    @Override
    public String toString() {
        Class<?> currClass = Timezone.class;
        String[] fieldNames = {
                "timezone"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
