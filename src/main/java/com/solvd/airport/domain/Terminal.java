package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Terminal {
    private String airportCode;
    private String terminalCode;
    private String terminalName;
    private boolean isInternational;
    private boolean isDomestic;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int TERMINAL_CODE_MAX_WIDTH = 10;
    final static private int TERMINAL_NAME_MAX_WIDTH = 100;

    public Terminal() {
    }

    public Terminal(String airportCode, String terminalCode, String terminalName, boolean isInternational, boolean isDomestic) {
        this.airportCode = airportCode;
        this.terminalCode = terminalCode;
        this.terminalName = terminalName;
        this.isInternational = isInternational;
        this.isDomestic = isDomestic;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
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

    @Override
    public String toString() {
        Class<?> currClass = Terminal.class;
        String[] fieldNames = {
                "airportCode",
                "terminalCode",
                "terminalName",
                "isInternational",
                "isDomestic"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
