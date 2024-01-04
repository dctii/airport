package com.solvd.airport.persistence;

import com.solvd.airport.util.SQLUtils;

public interface FlightStaffMemberDAO {

    String TABLE_NAME = "flight_staff";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_FLIGHT_STAFF_ID = "flight_staff_id";
    String COL_FLIGHT_CREW_ID = FlightCrewDAO.COL_FLIGHT_CREW_ID;
    String COL_AIRLINE_STAFF_ID = AirlineStaffMemberDAO.COL_AIRLINE_STAFF_ID;
    String EXPLICIT_COL_FLIGHT_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_STAFF_ID);
    String EXPLICIT_COL_FLIGHT_CREW_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CREW_ID);
    String EXPLICIT_COL_AIRLINE_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_STAFF_ID);

    // DAO methods
}
