package com.solvd.airport.persistence;

import com.solvd.airport.domain.AirlineStaffMember;
import org.apache.ibatis.annotations.Param;

public interface AirlineStaffMemberDAO {
    void createAirlineStaffMember(@Param("staffMemberObj") AirlineStaffMember staffMemberObj);
    AirlineStaffMember getAirlineStaffMemberById(@Param("airlineStaffId") int airlineStaffId);
    AirlineStaffMember findByPersonInfoId(@Param("personInfoId") int personInfoId);
    AirlineStaffMember findByEmailAddress(@Param("emailAddress") String emailAddress);
    void updateAirlineStaffMember(@Param("staffMemberObj") AirlineStaffMember staffMemberObj);
    void deleteAirlineStaffMember(@Param("airlineStaffId") int airlineStaffId);
}
