package com.solvd.airport.domain;

import com.solvd.airport.util.StringFormatters;

import java.sql.Date;

public class GovernmentId {
    private int govId;
    private String idType;
    private String idNumber;
    private Date issueDate;
    private Date expiryDate;
    private int personInfoId;

    public GovernmentId() {
    }

    public GovernmentId(int govId, String idType, String idNumber, Date issueDate,
                        Date expiryDate, int personInfoId) {
        this.govId = govId;
        this.idType = idType;
        this.idNumber = idNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.personInfoId = personInfoId;
    }

    public int getGovId() {
        return govId;
    }

    public void setGovId(int govId) {
        this.govId = govId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = this.getClass();
        String[] fieldNames = {
                "govId",
                "idType",
                "idNumber",
                "issueDate",
                "expiryDate",
                "personInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}


/*
CREATE TABLE
    government_ids (
        gov_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
        id_type VARCHAR(45),
        id_number VARCHAR(45),
        issue_date DATE,
        expiry_date DATE,
        person_info_id INT UNSIGNED NOT NULL,
        PRIMARY KEY(gov_id)
    );
    ALTER TABLE government_ids
    ADD
        FOREIGN KEY(person_info_id) REFERENCES person_info(person_info_id);
*/
