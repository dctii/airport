package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Terminal {
    private int terminalId;
    private String terminalCode;
    private String terminalName;
    private boolean isInternational;
    private boolean isDomestic;
    private boolean isPrivate;
    private String airportId;

    public Terminal() {
    }

    public Terminal(int terminalId, String terminalCode, String terminalName,
                    boolean isInternational, boolean isDomestic, boolean isPrivate,
                    String airportId) {
        this.terminalId = terminalId;
        this.terminalCode = terminalCode;
        this.terminalName = terminalName;
        this.isInternational = isInternational;
        this.isDomestic = isDomestic;
        this.isPrivate = isPrivate;
        this.airportId = airportId;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }

    public boolean isDomestic() {
        return isDomestic;
    }

    public void setDomestic(boolean domestic) {
        isDomestic = domestic;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "terminalId",
                "terminalCode",
                "terminalName",
                "isInternational",
                "isDomestic",
                "isPrivate",
                "airportId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    terminals (
        terminal_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        terminal_code VARCHAR(10) NOT NULL,
        terminal_name VARCHAR(100),
        is_international BOOL NOT NULL,
        is_domestic BOOL NOT NULL,
        is_private BOOL NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        PRIMARY KEY(terminal_id)
    );
    ALTER TABLE terminals
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);
*/
