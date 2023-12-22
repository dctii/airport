package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.sql.Timestamp;

public class BoardingPass {
    private int boardingPassId;
    private Timestamp boardingTime;
    private String boardingGroup;
    private int checkInId;
    private String flightId;
    private String baggageId;


    public BoardingPass() {
    }

    public BoardingPass(int boardingPassId, Timestamp boardingTime, String boardingGroup,
                        int checkInId, String flightId, String baggageId) {
        this.boardingPassId = boardingPassId;
        this.boardingTime = boardingTime;
        this.boardingGroup = boardingGroup;
        this.checkInId = checkInId;
        this.flightId = flightId;
        this.baggageId = baggageId;
    }

    public BoardingPass(Timestamp boardingTime, String boardingGroup,
                        int checkInId, String flightId, String baggageCode) {
        this.boardingTime = boardingTime;
        this.boardingGroup = boardingGroup;
        this.checkInId = checkInId;
        this.flightId = flightId;
        this.baggageId = baggageCode;
    }


    public int getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(int boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    public Timestamp getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Timestamp boardingTime) {
        this.boardingTime = boardingTime;
    }

    public String getBoardingGroup() {
        return boardingGroup;
    }

    public void setBoardingGroup(String boardingGroup) {
        this.boardingGroup = boardingGroup;
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(String baggageId) {
        this.baggageId = baggageId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "boardingPassId",
                "boardingTime",
                "boardingGroup",
                "checkInId",
                "flightId",
                "baggageId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    boarding_passes (
        boarding_pass_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        boarding_time DATETIME,
        boarding_group VARCHAR(25),
        check_in_id INT UNSIGNED NOT NULL,
        flight_id VARCHAR(10),
        baggage_id VARCHAR(45),
        PRIMARY KEY(boarding_pass_id)
    );
    ALTER TABLE boarding_passes
    ADD
        FOREIGN KEY (check_in_id) REFERENCES check_ins(check_in_id),
    ADD
        FOREIGN KEY (flight_id) REFERENCES flights(flight_code),
    ADD
        FOREIGN KEY (baggage_id) REFERENCES baggage(baggage_code);
*/
