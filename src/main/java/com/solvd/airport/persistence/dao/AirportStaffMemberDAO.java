package com.solvd.airport.persistence.dao;

import com.solvd.airport.domain.AirportStaffMember;

public interface AirportStaffMemberDAO {
    AirportStaffMember findByPersonInfoId(int personInfoId);
}
