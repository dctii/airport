package com.solvd.airport.persistence;

import com.solvd.airport.util.SQLUtils;

public interface AirlineDAO {
    String TABLE_NAME = "airlines";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRLINE_CODE = "airline_code";
    String COL_AIRLINE_NAME = "airline_name";
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_AIRLINE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_CODE);
    String EXPLICIT_COL_AIRLINE_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_NAME);
    String EXPLICIT_COL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);


    // insert DAO methods here
}
