package com.solvd.airport.persistence;

import com.solvd.airport.util.SQLUtils;

public interface FlightCrewDAO {

    String TABLE_NAME = "flight_crew";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_FLIGHT_CREW_ID = "flight_crew_id";
    String COL_FLIGHT_CODE = FlightDAO.COL_FLIGHT_CODE;
    String EXPLICIT_COL_FLIGHT_CREW_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CREW_ID);
    String EXPLICIT_COL_FLIGHT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CODE);

    // DAO methods
}
