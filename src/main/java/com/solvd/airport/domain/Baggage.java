package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.math.BigDecimal;

public class Baggage {
    private String baggageCode;
    private BigDecimal baggageWeight;
    private BigDecimal baggagePrice;
    private int boardingPassId;


    public Baggage() {
    }

    public Baggage(String baggageCode, BigDecimal baggageWeight,
                   BigDecimal baggagePrice, int boardingPassId) {
        this.baggageCode = baggageCode;
        this.baggageWeight = baggageWeight;
        this.baggagePrice = baggagePrice;
        this.boardingPassId = boardingPassId;
    }

    public Baggage(String baggageCode, BigDecimal baggageWeight, BigDecimal baggagePrice) {
        this.baggageCode = baggageCode;
        this.baggageWeight = baggageWeight;
        this.baggagePrice = baggagePrice;
    }


    public String getBaggageCode() {
        return baggageCode;
    }

    public void setBaggageCode(String baggageCode) {
        this.baggageCode = baggageCode;
    }

    public BigDecimal getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(BigDecimal baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    public BigDecimal getBaggagePrice() {
        return baggagePrice;
    }

    public void setBaggagePrice(BigDecimal baggagePrice) {
        this.baggagePrice = baggagePrice;
    }

    public int getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(int boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "baggageCode",
                "baggageWeight",
                "baggagePrice",
                "boardingPassId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    baggage (
        baggage_code VARCHAR(45) NOT NULL UNIQUE,
        baggage_weight DECIMAL(5, 2)
            CHECK (
                baggage_weight >= 0.00
            ),
        baggage_price DECIMAL(10, 2)
            CHECK (
                baggage_price >= 0.00
            ),
        boarding_pass_id INT UNSIGNED,
        PRIMARY KEY(baggage_code)
    );
    ALTER TABLE baggage
    ADD
        FOREIGN KEY(boarding_pass_id) REFERENCES boarding_passes(boarding_pass_id);
*/
