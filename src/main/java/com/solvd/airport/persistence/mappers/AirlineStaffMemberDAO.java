package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.AirlineStaffMember;

public interface AirlineStaffMemberDAO {
    void createAirlineStaffMember(AirlineStaffMember staffMember);
    AirlineStaffMember getAirlineStaffMemberById(int id);
    AirlineStaffMember findByPersonInfoId(int personInfoId);
    void updateAirlineStaffMember(AirlineStaffMember staffMember);
    void deleteAirlineStaffMember(int id);
}
