package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class SecurityCheckpoint {
    private int securityCheckpointId;
    private String stationCode;
    private int terminalId;
    private String airportId;
    private int airportTeamId;

    public SecurityCheckpoint() {
    }

    public SecurityCheckpoint(int securityCheckpointId, String stationCode, int terminalId,
                              String airportId, int airportTeamId) {
        this.securityCheckpointId = securityCheckpointId;
        this.stationCode = stationCode;
        this.terminalId = terminalId;
        this.airportId = airportId;
        this.airportTeamId = airportTeamId;
    }

    public int getSecurityCheckpointId() {
        return securityCheckpointId;
    }

    public void setSecurityCheckpointId(int securityCheckpointId) {
        this.securityCheckpointId = securityCheckpointId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public int getAirportTeamId() {
        return airportTeamId;
    }

    public void setAirportTeamId(int airportTeamId) {
        this.airportTeamId = airportTeamId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "securityCheckpointId",
                "stationCode",
                "terminalId",
                "airportId",
                "airportTeamId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}

/*
CREATE TABLE
    security_checkpoints (
        security_checkpoint_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        station_code VARCHAR(45) NOT NULL UNIQUE,
        terminal_id INT UNSIGNED NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        airport_team_id INT UNSIGNED,
        PRIMARY KEY(security_checkpoint_id)
    );
    ALTER TABLE security_checkpoints
    ADD
        FOREIGN KEY(airport_team_id) REFERENCES airport_teams(airport_team_id),
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id),
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);
*/

