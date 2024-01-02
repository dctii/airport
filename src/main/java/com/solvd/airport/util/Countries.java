package com.solvd.airport.util;

public enum Countries {
    US("US", "United States of America"),
    CN("CN", "China"),
    JP("JP", "Japan"),
    GB("GB", "United Kingdom of Great Britain and Northern Ireland"),
    BY("BY", "Belarus"),
    CA("CA", "Canada"),
    AU("AU", "Australia"),
    DE("DE", "Germany"),
    FR("FR", "France"),
    RU("RU", "Russia"),
    BR("BR", "Brazil"),
    IN("IN", "India"),
    MX("MX", "Mexico"),
    ES("ES", "Spain"),
    IT("IT", "Italy"),
    AR("AR", "Argentina");

    private final String countryCode; // ISO 3166 alpha-2 code
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
