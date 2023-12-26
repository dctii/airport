package com.solvd.airport.util;

public enum Countries {
    US("US", "United States of America"),
    CN("CN", "China"),
    JP("JP", "Japan"),
    GB("GB", "United Kingdom of Great Britain and Northern Ireland"),
    BY("BY", "Belarus");

    private final String countryCode;
    private final String countryName;

    Countries(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
