package com.solvd.airport.persistence;

import com.solvd.airport.domain.Terminal;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface TerminalDAO {

    void createTerminal(@Param("terminalObj") Terminal terminalObj);

    boolean doesTerminalExist(@Param("airportCode") String airportCode, @Param("terminalCode") String terminalCode);

    String TABLE_NAME = "terminals";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRPORT_CODE = AirportDAO.COL_AIRPORT_CODE;
    String COL_TERMINAL_CODE = "terminal_code";
    String COL_TERMINAL_NAME = "terminal_name";
    String COL_IS_INTERNATIONAL = "is_international";
    String COL_IS_DOMESTIC = "is_domestic";
    String EXPLICIT_COL_AIRPORT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_CODE);
    String EXPLICIT_COL_TERMINAL_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TERMINAL_CODE);
    String EXPLICIT_COL_TERMINAL_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TERMINAL_NAME);
    String EXPLICIT_COL_IS_INTERNATIONAL = SQLUtils.qualifyColumnName(TABLE_NAME, COL_IS_INTERNATIONAL);
    String EXPLICIT_COL_IS_DOMESTIC = SQLUtils.qualifyColumnName(TABLE_NAME, COL_IS_DOMESTIC);

    // DAO methods

}
