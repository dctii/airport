package com.solvd.airport.persistence;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface AirportDAO extends AbstractDAO<Airport> {
    int create(@Param("airportObj") Airport airportObj);

    Airport getById(@Param("airportId") int airportId);

    Airport getAirportByCode(@Param("airportCode") String airportCode);

    void update(@Param("airportObj") Airport airportObj);

    void delete(@Param("airportId") int airportId);

    void deleteAirportByCode(@Param("airportCode") String airportCode);

    boolean doesAirportExist(@Param("airportCode") String airportCode);

    Set<Terminal> getTerminalsByAirportCode(@Param("airportCode") String airportCode);

    String TABLE_NAME = "airports";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRPORT_ID = "airport_id";
    String COL_AIRPORT_CODE = "airport_code";
    String COL_AIRPORT_NAME = "airport_name";
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_AIRPORT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_CODE);
    String EXPLICIT_COL_AIRPORT_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_NAME);
    String EXPLICIT_COL_AIRPORT_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);

}
