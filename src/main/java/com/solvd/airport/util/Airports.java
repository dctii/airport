package com.solvd.airport.util;

public enum Airports {
    LAX(
            "LAX",
            "Los Angeles International Airport",
            "1 World Way",
            null,
            "Los Angeles",
            "California",
            "US",
            "90045",
            "America/Los_Angeles"
    ),
    JFK(
            "JFK",
            "John F. Kennedy International Airport",
            "JFK Access Road",
            "Queens",
            "New York",
            "New York",
            "US",
            "11430",
            "America/New_York"
    ),
    HND(
            "HND",
            "Tokyo International Airport",
            "3-3-2 Haneda-Kuko",
            "Ota-ku",
            "Tokyo",
            "Tokyo",
            "JP",
            "144-0041",
            "Asia/Tokyo"
    ),
    LHR(
            "LHR",
            "London Heathrow",
            "Nelson Road",
            "Hounslow",
            "London",
            "England",
            "GB",
            "TW6 2GW",
            "Europe/London"
    ),
    MSQ(
            "MSQ",
            "Minsk National Airport",
            "Minsk National Airport",
            "Petrovichskiy",
            "Minsk",
            "Minsk Region",
            "BY",
            "222224",
            "Europe/Minsk"
    );

    private final String airportCode;
    private final String airportName;
    private final String street;
    private final String citySubdivision;
    private final String city;
    private final String citySuperdivision;
    private final String countryCode;
    private final String postalCode;
    private final String timezone;

    Airports(
            String airportCode,
            String airportName,
            String street,
            String citySubdivision,
            String city,
            String citySuperdivision,
            String countryCode,
            String postalCode,
            String timezone
    ) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.street = street;
        this.citySubdivision = citySubdivision;
        this.city = city;
        this.citySuperdivision = citySuperdivision;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.timezone = timezone;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getStreet() {
        return street;
    }

    public String getCitySubdivision() {
        return citySubdivision;
    }

    public String getCity() {
        return city;
    }

    public String getCitySuperdivision() {
        return citySuperdivision;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTimezone() {
        return timezone;
    }
}
