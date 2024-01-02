package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.Map;

public class Gate {
    private int gateId;
    private String gateCode;
    private String airportCode;
    private String terminalCode;

    final static private int GATE_CODE_MAX_WIDTH = 10;
    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int TERMINAL_CODE_MAX_WIDTH = 10;


    public Gate() {
    }


    public Gate(int gateId, String gateCode, String airportCode, String terminalCode) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        gateCode, GATE_CODE_MAX_WIDTH,
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        terminalCode, TERMINAL_CODE_MAX_WIDTH
                ));

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
        ExceptionUtils.isStringLengthValid(gateCode, GATE_CODE_MAX_WIDTH);

        this.gateCode = gateCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        ExceptionUtils.isStringLengthValid(airportCode, AIRPORT_CODE_MAX_WIDTH);

        this.airportCode = airportCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        ExceptionUtils.isStringLengthValid(terminalCode, TERMINAL_CODE_MAX_WIDTH);

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
