package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Timezone {
    private int timezoneId;
    private String timezoneName;

    public Timezone() {
    }

    public Timezone(int timezoneId, String timezoneName) {
        this.timezoneId = timezoneId;
        this.timezoneName = timezoneName;
    }

    public int getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(int timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "timezoneId",
                "timezoneName"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

/*
CREATE TABLE
    timezones (
        timezone_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        timezone_name VARCHAR(45) NOT NULL UNIQUE,
        PRIMARY KEY(timezone_id)
    );
*/
