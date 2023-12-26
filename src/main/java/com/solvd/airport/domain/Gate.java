package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Gate {
    private int gateId;
    private String gateCode;
    private int airportCode;
    private int terminalCode;

    // TODO: Add length and precision checks from ExceptionUtils
    final static private int GATE_CODE_MAX_WIDTH = 10;
    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int TERMINAL_CODE_MAX_WIDTH = 10;


    public Gate() {
    }

    public Gate(int gateId, String gateCode, int airportCode, int terminalCode) {
        this.gateId = gateId;
        this.gateCode = gateCode;
        this.airportCode = airportCode;
        this.terminalCode = terminalCode;
    }

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public String getGateCode() {
        return gateCode;
    }

    public void setGateCode(String gateCode) {
        this.gateCode = gateCode;
    }

    public int getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(int airportCode) {
        this.airportCode = airportCode;
    }

    public int getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(int terminalCode) {
        this.terminalCode = terminalCode;
    }

    @Override
    public String toString() {
        Class<?> currClass = Gate.class;
        String[] fieldNames = {
                "gateId",
                "gateCode",
                "terminalCode",
                "airportCode"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
