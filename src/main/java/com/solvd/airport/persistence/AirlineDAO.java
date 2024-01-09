package com.solvd.airport.persistence;

import com.solvd.airport.domain.Airline;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface AirlineDAO {
    void createAirline(@Param("airlineObj") Airline airlineObj);

    void createAirlineWithoutAddress(@Param("airlineObj") Airline airlineObj);

    boolean doesAirlineExist(@Param("airlineCode") String airlineCode);

    String TABLE_NAME = "airlines";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRLINE_CODE = "airline_code";
    String COL_AIRLINE_NAME = "airline_name";
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_AIRLINE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_CODE);
    String EXPLICIT_COL_AIRLINE_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_NAME);
    String EXPLICIT_COL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);

}
