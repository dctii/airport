package com.solvd.airport.persistence;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface AirportDAO {
    void createAirport(@Param("airportObj") Airport airportObj);

    void createAirportWithoutAddress(@Param("airportObj") Airport airportObj);

    Airport getAirportByCode(@Param("airportCode") String airportCode);

    void updateAirport(@Param("airportObj") Airport airportObj);


    void deleteAirport(@Param("airportCode") String airportCode);

    boolean doesAirportExist(@Param("airportCode") String airportCode);

    String TABLE_NAME = "airports";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRPORT_CODE = "airport_code";
    String COL_AIRPORT_NAME = "airport_name";
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_AIRPORT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_CODE);
    String EXPLICIT_COL_AIRPORT_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_NAME);
    String EXPLICIT_COL_AIRPORT_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);

}
