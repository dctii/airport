package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class FlightStaffMember {
    private int flightStaffId;
    private String memberRole;
    private int personInfoId;
    private String airlineId;
    private int flightCrewId;

    public FlightStaffMember() {
    }

    public FlightStaffMember(int flightStaffId, String memberRole, int personInfoId,
                             String airlineId, int flightCrewId) {
        this.flightStaffId = flightStaffId;
        this.memberRole = memberRole;
        this.personInfoId = personInfoId;
        this.airlineId = airlineId;
        this.flightCrewId = flightCrewId;
    }

    public int getFlightStaffId() {
        return flightStaffId;
    }

    public void setFlightStaffId(int flightStaffId) {
        this.flightStaffId = flightStaffId;
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

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public int getFlightCrewId() {
        return flightCrewId;
    }

    public void setFlightCrewId(int flightCrewId) {
        this.flightCrewId = flightCrewId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "flightStaffId",
                "memberRole",
                "personInfoId",
                "airlineId",
                "flightCrewId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    flight_staff (
        flight_staff_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        member_role VARCHAR(45) NOT NULL,
        person_info_id INT UNSIGNED NOT NULL,
        airline_id VARCHAR(2) NOT NULL,
        flight_crew_id INT UNSIGNED,
        PRIMARY KEY(flight_staff_id)
    );
    ALTER TABLE flight_staff
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id),
    ADD
        FOREIGN KEY(airline_id) REFERENCES airlines(IATA_airline_code),
    ADD
        FOREIGN KEY(flight_crew_id) REFERENCES flight_crew(flight_crew_id);
*/
