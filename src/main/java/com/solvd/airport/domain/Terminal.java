package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// StAX
public class Terminal {
    private int terminalId;
    private String airportCode;
    private String terminalCode;
    private String terminalName;
    private boolean isInternational;
    private boolean isDomestic;


    private Set<Gate> gates;

    final static private int AIRPORT_CODE_MAX_WIDTH = 3;
    final static private int TERMINAL_CODE_MAX_WIDTH = 10;
    final static private int TERMINAL_NAME_MAX_WIDTH = 100;

    public Terminal() {
    }

    public Terminal(String airportCode, String terminalCode, String terminalName,
                    boolean isInternational, boolean isDomestic) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        terminalCode, TERMINAL_CODE_MAX_WIDTH,
                        terminalName, TERMINAL_NAME_MAX_WIDTH
                )
        );

        this.airportCode = airportCode;
        this.terminalCode = terminalCode;
        this.terminalName = terminalName;
        this.isInternational = isInternational;
        this.isDomestic = isDomestic;
    }

    public Terminal(int terminalId, String airportCode, String terminalCode, String terminalName,
                    boolean isInternational, boolean isDomestic) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        terminalCode, TERMINAL_CODE_MAX_WIDTH,
                        terminalName, TERMINAL_NAME_MAX_WIDTH
                )
        );

        this.terminalId = terminalId;
        this.airportCode = airportCode;
        this.terminalCode = terminalCode;
        this.terminalName = terminalName;
        this.isInternational = isInternational;
        this.isDomestic = isDomestic;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
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

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        ExceptionUtils.isStringLengthValid(terminalName, TERMINAL_NAME_MAX_WIDTH);

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

    public Set<Gate> getGates() {
        if (gates == null) {
            gates = new HashSet<>();
        }
        return gates;
    }

    public void setGates(Set<Gate> gates) {
        this.gates = gates;
    }

    public void addGate(Gate gate) {
        if (this.gates == null) {
            this.gates = new HashSet<>();
        }
        this.gates.add(gate);
    }

    public boolean removeGate(Gate gate) {
        return this.gates != null && this.gates.remove(gate);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.TERMINAL;
        String[] fieldNames = {
                "terminalId",
                "airportCode",
                "terminalCode",
                "terminalName",
                "isInternational",
                "isDomestic",
                "gates"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
