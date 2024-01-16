package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// JAXB

@XmlAccessorType(XmlAccessType.FIELD)
public class Gate {
    @XmlAttribute(name = "id")
    private int gateId;
    @XmlElement(name = "gateCode")
    private String gateCode;
    @XmlElement(name = "airportCode")
    private String airportCode;

    @XmlElement(name = "terminalCode")
    private String terminalCode;

    private Set<Flight> flights;

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

    public Gate(String gateCode, String airportCode, String terminalCode) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        gateCode, GATE_CODE_MAX_WIDTH,
                        airportCode, AIRPORT_CODE_MAX_WIDTH,
                        terminalCode, TERMINAL_CODE_MAX_WIDTH
                ));

        this.gateCode = gateCode;
        this.airportCode = airportCode;
        this.terminalCode = terminalCode;
    }

    public Gate(String gateCode, String airportCode) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        gateCode, GATE_CODE_MAX_WIDTH,
                        airportCode, AIRPORT_CODE_MAX_WIDTH
                        ));

        this.gateCode = gateCode;
        this.airportCode = airportCode;
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

    public Set<Flight> getFights() {
        if (flights == null) {
            flights = new HashSet<>();
        }
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        if (this.flights == null) {
            this.flights = new HashSet<>();
        }
        this.flights.add(flight);
    }

    public boolean removeFlight(Flight flight) {
        return this.flights != null && this.flights.remove(flight);
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.GATE;
        String[] fieldNames = {
                "gateId",
                "gateCode",
                "terminalCode",
                "airportCode",
                "flights"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
