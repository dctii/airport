package com.solvd.airport.util;

public enum Timezones {
    AMERICA_LOS_ANGELES("America/Los_Angeles"),
    AMERICA_NEW_YORK("America/New_York"),
    ASIA_TOKYO("Asia/Tokyo"),
    EUROPE_LONDON("Europe/London"),
    EUROPE_MINSK("Europe/Minsk");

    private final String tzString;

    Timezones(String tzString) {
        this.tzString = tzString;
    }

    public String getTzString() {
        return tzString;
    }
}
