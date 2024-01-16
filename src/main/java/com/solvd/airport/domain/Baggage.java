package com.solvd.airport.domain;

import com.solvd.airport.util.ArrayUtils;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

import java.math.BigDecimal;
import java.util.Map;

public class Baggage {
    private int baggageId;
    private String baggageCode;
    private BigDecimal weight;
    private BigDecimal price;
    private Integer checkInId;

    final static private int BAGGAGE_CODE_MAX_WIDTH = 45;
    final static private int WEIGHT_DECIMAL_PRECISION = 5;
    final static private int WEIGHT_DECIMAL_SCALE = 2;
    final static private int PRICE_DECIMAL_PRECISION = 10;
    final static private int PRICE_DECIMAL_SCALE = 2;


    public Baggage() {
    }

    public Baggage(String baggageCode) {
        this.baggageCode = baggageCode;
    }

    public Baggage(int baggageId,  String baggageCode) {
        this.baggageId = baggageId;
        this.baggageCode = baggageCode;
    }

    public Baggage(String baggageCode, BigDecimal weight,
                   BigDecimal price, int checkInId) {
        ExceptionUtils.isStringLengthValid(baggageCode, BAGGAGE_CODE_MAX_WIDTH);

        ExceptionUtils.areDecimalsValid(
                Map.of(
                        weight, ArrayUtils.intArrayOf(WEIGHT_DECIMAL_PRECISION, WEIGHT_DECIMAL_SCALE),
                        price, ArrayUtils.intArrayOf(PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE)
                )
        );


        this.baggageCode = baggageCode;
        this.weight = weight;
        this.price = price;
        this.checkInId = checkInId;
    }

    public Baggage(int baggageId, String baggageCode, BigDecimal weight,
                   BigDecimal price, int checkInId) {
        ExceptionUtils.isStringLengthValid(baggageCode, BAGGAGE_CODE_MAX_WIDTH);

        ExceptionUtils.areDecimalsValid(
                Map.of(
                        weight, ArrayUtils.intArrayOf(WEIGHT_DECIMAL_PRECISION, WEIGHT_DECIMAL_SCALE),
                        price, ArrayUtils.intArrayOf(PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE)
                )
        );

        this.baggageId = baggageId;
        this.baggageCode = baggageCode;
        this.weight = weight;
        this.price = price;
        this.checkInId = checkInId;
    }

    public int getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(int baggageId) {
        this.baggageId = baggageId;
    }

    public String getBaggageCode() {
        return baggageCode;
    }

    public void setBaggageCode(String baggageCode) {
        ExceptionUtils.isStringLengthValid(baggageCode, BAGGAGE_CODE_MAX_WIDTH);

        this.baggageCode = baggageCode;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        ExceptionUtils.isDecimalValid(weight, WEIGHT_DECIMAL_PRECISION, WEIGHT_DECIMAL_SCALE);

        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        ExceptionUtils.isDecimalValid(price, PRICE_DECIMAL_PRECISION, PRICE_DECIMAL_SCALE);

        this.price = price;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.BAGGAGE;
        String[] fieldNames = {
                "baggageId",
                "baggageCode",
                "weight",
                "price",
                "checkInId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
