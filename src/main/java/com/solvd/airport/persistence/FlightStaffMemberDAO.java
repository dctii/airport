package com.solvd.airport.persistence;

import com.solvd.airport.domain.FlightStaffMember;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface FlightStaffMemberDAO extends AbstractDAO<FlightStaffMember> {

    int create(@Param("flightStaffMemberObj") FlightStaffMember flightStaffMemberObj);

    FlightStaffMember getById(@Param("flightStaffMemberObj") int flightStaffMemberId);

    void update(@Param("flightStaffMemberObj") FlightStaffMember flightStaffMemberObj);

    void delete(@Param("flightStaffMemberId") int flightStaffMemberId);

    String TABLE_NAME = "flight_staff";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_FLIGHT_STAFF_ID = "flight_staff_id";
    String COL_FLIGHT_CREW_ID = "flight_crew_id";
    String COL_AIRLINE_STAFF_ID = AirlineStaffMemberDAO.COL_AIRLINE_STAFF_ID;
    String EXPLICIT_COL_FLIGHT_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_STAFF_ID);
    String EXPLICIT_COL_FLIGHT_CREW_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CREW_ID);
    String EXPLICIT_COL_AIRLINE_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_STAFF_ID);

    String FLIGHT_CREW_TABLE_NAME = "flight_crew";
    String FC_COL_FLIGHT_CREW_ID = COL_FLIGHT_CREW_ID;
    String FC_COL_FLIGHT_CODE = FlightDAO.COL_FLIGHT_CODE;
    String FC_EXPLICIT_COL_FLIGHT_CREW_ID = SQLUtils.qualifyColumnName(FLIGHT_CREW_TABLE_NAME, FC_COL_FLIGHT_CREW_ID);
    String FC_EXPLICIT_COL_FLIGHT_CODE = SQLUtils.qualifyColumnName(FLIGHT_CREW_TABLE_NAME, FC_COL_FLIGHT_CODE);

}
