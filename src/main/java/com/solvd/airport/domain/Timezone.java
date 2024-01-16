package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class Timezone {
    private int timezoneId;
    private String timezone;

    final static private int TIMEZONE_MAX_WIDTH = 45;

    public Timezone() {
    }

    public Timezone(String timezone) {
        ExceptionUtils.isStringLengthValid(timezone, TIMEZONE_MAX_WIDTH);
        ExceptionUtils.isValidTimeZone(timezone);

        this.timezone = timezone;
    }

    public Timezone(int timezoneId, String timezone) {
        ExceptionUtils.isStringLengthValid(timezone, TIMEZONE_MAX_WIDTH);
        ExceptionUtils.isValidTimeZone(timezone);

        this.timezoneId = timezoneId;
        this.timezone = timezone;
    }

    public int getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(int timezoneId) {
        this.timezoneId = timezoneId;
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
        Class<?> currClass = ClassConstants.TIMEZONE;
        String[] fieldNames = {
                "timezoneId",
                "timezone"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
