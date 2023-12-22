package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

public class Gate {
    private int gateId;
    private String gateCode;
    private int terminalId;

    public Gate() {
    }

    public Gate(int gateId, String gateCode, int terminalId) {
        this.gateId = gateId;
        this.gateCode = gateCode;
        this.terminalId = terminalId;
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
        this.gateCode = gateCode;
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
                "gateId",
                "gateCode",
                "terminalId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    gates (
        gate_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        gate_code VARCHAR(10) NOT NULL,
        terminal_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(gate_id)
    );
    ALTER TABLE gates
    ADD
        FOREIGN KEY(terminal_id) REFERENCES terminals(terminal_id);
*/
