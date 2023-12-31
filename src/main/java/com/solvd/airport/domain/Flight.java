package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringConstants;
import com.solvd.airport.util.StringFormatters;

import java.sql.Timestamp;
import java.util.Map;

public class Flight {
    private String flightCode;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String destination; // airport code, 3 chars
    private String airlineCode;
    private int gateId; // departure gate
    private String aircraftModel;
    private int passengerCapacity;
    private String tailNumber;

    final private static int FLIGHT_CODE_MAX_WIDTH = 10;
    final private static int DESTINATION_AIRPORT_MAX_WIDTH = 3;
    final private static int AIRLINE_CODE_MAX_WIDTH = 2;
    final private static int AIRCRAFT_MODEL_MAX_WIDTH = 45;
    final private static int TAIL_NUMBER_MAX_WIDTH = 45;

    public Flight() {
    }

    public Flight(String flightCode, Timestamp departureTime, Timestamp arrivalTime,
                  String destination, String airlineCode, int gateId, String aircraftModel,
                  int passengerCapacity, String tailNumber) {
        ExceptionUtils.areStringLengthsValid(
                Map.of(
                        flightCode, FLIGHT_CODE_MAX_WIDTH,
                        destination, DESTINATION_AIRPORT_MAX_WIDTH,
                        airlineCode, AIRLINE_CODE_MAX_WIDTH,
                        aircraftModel, AIRCRAFT_MODEL_MAX_WIDTH,
                        tailNumber, TAIL_NUMBER_MAX_WIDTH
                )
        );

        this.flightCode = flightCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.destination = destination;
        this.airlineCode = airlineCode;
        this.gateId = gateId;
        this.aircraftModel = aircraftModel;
        this.passengerCapacity = passengerCapacity;
        this.tailNumber = tailNumber;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        ExceptionUtils.isStringLengthValid(flightCode, FLIGHT_CODE_MAX_WIDTH);

        this.flightCode = flightCode;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public void setDepartureTime(String departureTime) {
        ExceptionUtils.isValidTimestamp(
                departureTime,
                StringConstants.TIMESTAMP_PATTERN
        );

        this.departureTime = SQLUtils.toTimestamp(departureTime);
    }

    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ExceptionUtils.isValidTimestamp(
                arrivalTime,
                StringConstants.TIMESTAMP_PATTERN
        );

        this.arrivalTime = SQLUtils.toTimestamp(arrivalTime);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        ExceptionUtils.isStringLengthValid(destination, DESTINATION_AIRPORT_MAX_WIDTH);
        this.destination = destination;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        ExceptionUtils.isStringLengthValid(airlineCode, AIRLINE_CODE_MAX_WIDTH);

        this.airlineCode = airlineCode;
    }

    public int getGateId() {
        return gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        ExceptionUtils.isStringLengthValid(aircraftModel, AIRCRAFT_MODEL_MAX_WIDTH);
        this.aircraftModel = aircraftModel;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        ExceptionUtils.isStringLengthValid(tailNumber, TAIL_NUMBER_MAX_WIDTH);
        this.tailNumber = tailNumber;
    }

    @Override
    public String toString() {
        Class<?> currClass = Flight.class;
        String[] fieldNames = {
                "flightCode",
                "departureTime",
                "arrivalTime",
                "destination",
                "airlineCode",
                "gateId",
                "aircraftModel",
                "passengerCapacity",
                "tailNumber"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
