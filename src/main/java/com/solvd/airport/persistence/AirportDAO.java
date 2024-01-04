package com.solvd.airport.persistence;

import com.solvd.airport.util.SQLUtils;

public interface AirportDAO {
    String TABLE_NAME = "airports";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRPORT_CODE = "airport_code";
    String COL_AIRPORT_NAME = "airport_name";
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_AIRPORT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_CODE);
    String EXPLICIT_COL_AIRPORT_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_NAME);
    String EXPLICIT_COL_AIRPORT_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);

    // DAO methods
}
