package com.solvd.airport.util;

public enum BookingAgencies {
    KAYAK("Kayak"),
    EXPEDIA("Expedia"),
    ORBITZ("Orbitz"),
    TRAVELOCITY("Travelocity")
    ;

    private final String agencyName;

    BookingAgencies(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyName() {
        return agencyName;
    }
}
