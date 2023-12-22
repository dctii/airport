package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class AirportTeam {
    private int airportTeamId;
    private String stationCode;
    private String airportId;
    private int terminalId;


    public AirportTeam() {
    }

    public AirportTeam(int airportTeamId, String stationCode, String airportId, int terminalId) {
        this.airportTeamId = airportTeamId;
        this.stationCode = stationCode;
        this.airportId = airportId;
        this.terminalId = terminalId;
    }


    public int getAirportTeamId() {
        return airportTeamId;
    }

    public void setAirportTeamId(int airportTeamId) {
        this.airportTeamId = airportTeamId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "airportTeamId",
                "stationCode",
                "airportId",
                "terminalId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}



/*
CREATE TABLE
    airport_teams (
        airport_team_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        station_code VARCHAR(45) NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        terminal_id INT UNSIGNED,
        PRIMARY KEY(airport_team_id)
    );
    ALTER TABLE airport_teams
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code),
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id);
*/
