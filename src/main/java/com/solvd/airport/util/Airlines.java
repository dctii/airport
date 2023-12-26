package com.solvd.airport.util;

public enum Airlines {
    DELTA(
            "DL",
            "Delta Air Lines",
            "1030 Delta Boulevard",
            "Atlanta",
            "Atlanta",
            "Georgia",
            "US",
            "30354",
            "America/New_York"
    ),
    BELAVIA(
            "B2",
            "Belavia Belarusian Airlines",
            "14A Nemiga Street",
            "Minsk",
            "Minsk",
            "Minsk Region",
            "BY",
            "220004",
            "Europe/Minsk"
    ),
    AIR_JAPAN(
            "JX",
            "Air Japan",
            "ANA House",
            "Minato",
            "Tokyo",
            "Tokyo",
            "JP",
            "105-7133",
            "Asia/Tokyo"
    ),
    BRITISH_AIRWAYS(
            "BA",
            "British Airways",
            "Waterside, PO Box 365",
            "Harmondsworth",
            "London",
            "Greater London",
            "GB",
            "UB7 0GB",
            "Europe/London"
    );

    private final String airlineCode;
    private final String airlineName;
    private final String street;
    private final String city;
    private final String citySubdivision;
    private final String citySuperdivision;
    private final String countryCode;
    private final String postalCode;
    private final String timezone;

    Airlines(
            String airlineCode,
            String airlineName,
            String street,
            String city,
            String citySubdivision,
            String citySuperdivision,
            String countryCode,
            String postalCode,
            String timezone
    ) {
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.street = street;
        this.city = city;
        this.citySubdivision = citySubdivision;
        this.citySuperdivision = citySuperdivision;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.timezone = timezone;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCitySubdivision() {
        return citySubdivision;
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
