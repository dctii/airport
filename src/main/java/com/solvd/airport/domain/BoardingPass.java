package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.sql.Timestamp;

public class BoardingPass {
    private int boardingPassId;
    private boolean isBoarded;
    private Timestamp boardingTime;
    private String boardingGroup;
    private int checkInId;

    final static private int BOARDING_GROUP_MAX_WIDTH = 25;


    public BoardingPass() {
    }

    public BoardingPass(int boardingPassId, int checkInId) {
        this.boardingPassId = boardingPassId;
        this.checkInId = checkInId;
    }

    public BoardingPass(int boardingPassId, boolean isBoarded, Timestamp boardingTime, String boardingGroup, int checkInId) {
        ExceptionUtils.isStringLengthValid(boardingGroup, BOARDING_GROUP_MAX_WIDTH);

        this.boardingPassId = boardingPassId;
        this.isBoarded = isBoarded;
        this.boardingTime = boardingTime;
        this.boardingGroup = boardingGroup;
        this.checkInId = checkInId;
    }

    public BoardingPass(boolean isBoarded, Timestamp boardingTime, String boardingGroup, int checkInId) {
        this.isBoarded = isBoarded;
        this.boardingTime = boardingTime;
        this.boardingGroup = boardingGroup;
        this.checkInId = checkInId;
    }

    public int getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(int boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    public boolean isBoarded() {
        return isBoarded;
    }

    public void setBoarded(boolean boarded) {
        isBoarded = boarded;
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
        ExceptionUtils.isStringLengthValid(boardingGroup, BOARDING_GROUP_MAX_WIDTH);

        this.boardingGroup = boardingGroup;
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    @Override
    public String toString() {
        Class<?> currClass = BoardingPass.class;
        String[] fieldNames = {
                "boardingPassId",
                "isBoarded",
                "boardingTime",
                "boardingGroup",
                "checkInId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
