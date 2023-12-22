package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class AirportStaffMember {
    private int airportStaffId;
    private String memberRole;
    private int personInfoId;
    private String airportId;
    private int airportTeamId;

    public AirportStaffMember() {
    }

    public AirportStaffMember(int airportStaffId, String memberRole, int personInfoId, String airportId, int airportTeamId) {
        this.airportStaffId = airportStaffId;
        this.memberRole = memberRole;
        this.personInfoId = personInfoId;
        this.airportId = airportId;
        this.airportTeamId = airportTeamId;
    }

    public int getAirportStaffId() {
        return airportStaffId;
    }

    public void setAirportStaffId(int airportStaffId) {
        this.airportStaffId = airportStaffId;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
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
                "airportStaffId",
                "memberRole",
                "personInfoId",
                "airportId",
                "airportTeamId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    airport_staff (
        airport_staff_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        member_role VARCHAR(45) NOT NULL,
        person_info_id INT UNSIGNED NOT NULL,
        airport_id VARCHAR(3) NOT NULL,
        airport_team_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(airport_staff_id)
    );
    ALTER TABLE airport_staff
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id),
    ADD
        FOREIGN KEY(airport_id) REFERENCES airports(IATA_airport_code);
*/
