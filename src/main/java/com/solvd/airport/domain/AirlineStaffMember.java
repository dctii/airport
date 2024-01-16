package com.solvd.airport.domain;

import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.ExceptionUtils;
import com.solvd.airport.util.StringFormatters;

public class AirlineStaffMember {
    private int airlineStaffId;
    private String memberRole;
    //    private Integer personInfoId;
    private PersonInfo personInfo;

    final static private int MEMBER_ROLE_MAX_WIDTH = 45;

    public AirlineStaffMember() {
    }

    public AirlineStaffMember(int airlineStaffId, String memberRole) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.airlineStaffId = airlineStaffId;
        this.memberRole = memberRole;
    }

    public AirlineStaffMember(int airlineStaffId, String memberRole, PersonInfo personInfo) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.airlineStaffId = airlineStaffId;
        this.memberRole = memberRole;
        this.personInfo = personInfo;
    }


    public AirlineStaffMember(String memberRole, PersonInfo personInfo) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.memberRole = memberRole;
        this.personInfo = personInfo;
    }

    public AirlineStaffMember(int airlineStaffId, String memberRole, int personInfoId) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.airlineStaffId = airlineStaffId;
        this.memberRole = memberRole;
        this.personInfo = new PersonInfo(personInfoId);
    }


    public AirlineStaffMember(String memberRole, int personInfoId) {
        ExceptionUtils.isStringLengthValid(memberRole, MEMBER_ROLE_MAX_WIDTH);

        this.memberRole = memberRole;
        this.personInfo = new PersonInfo(personInfoId);
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public Integer getPersonInfoId() {
        if (this.personInfo != null) {
            return this.personInfo.getPersonInfoId();
        } else {
            return null;
        }
    }

    public void setPersonInfoId(int personInfoId) {
        if (this.personInfo == null) {
            this.personInfo = new PersonInfo();
        }
        this.personInfo.setPersonInfoId(personInfoId);
    }

    public EmailAddress getEmailAddressByEmailAddress(String email) {
        return personInfo.getEmailAddressByEmailAddress(email);
    }


    @Override
    public String toString() {
        Class<?> currClass = ClassConstants.AIRLINE_STAFF_MEMBER;
        String[] fieldNames = {
                "airlineStaffId",
                "memberRole",
                "personInfo"
        };

        String fieldsString = StringFormatters.buildFieldsString(this, fieldNames);
        return StringFormatters.buildToString(currClass, fieldNames, fieldsString);
    }
}
