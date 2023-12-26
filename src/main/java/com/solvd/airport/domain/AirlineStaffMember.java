package com.solvd.airport.domain;

import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class AirlineStaffMember {
    private int airlineStaffId;
    private String memberRole;
    private int personInfoId;

    final static private int MEMBER_ROLE_MAX_WIDTH = 45;

    public AirlineStaffMember() {
    }

    public AirlineStaffMember(int airlineStaffId, String memberRole, int personInfoId) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.airlineStaffId = airlineStaffId;
        this.memberRole = memberRole;
        this.personInfoId = personInfoId;
    }

    public int getAirlineStaffId() {
        return airlineStaffId;
    }

    public void setAirlineStaffId(int airlineStaffId) {
        this.airlineStaffId = airlineStaffId;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.memberRole = memberRole;
    }

    public int getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(int personInfoId) {
        this.personInfoId = personInfoId;
    }

    @Override
    public String toString() {
        Class<?> currClass = AirlineStaffMember.class;
        String[] fieldNames = {
                "airlineStaffId",
                "memberRole",
                "personInfoId"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
