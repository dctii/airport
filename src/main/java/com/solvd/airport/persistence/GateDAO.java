package com.solvd.airport.persistence;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface GateDAO {

    void createGate(@Param("gateObj") Gate gateObj);

    boolean doesGateExist(
            @Param("airportCode") String airportCode,
            @Param("terminalCode") String terminalCode,
            @Param("gateCode") String gateCode
    );

    String TABLE_NAME = "gates";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_GATE_ID = "gate_id";
    String COL_GATE_CODE = "gate_code";
    String COL_AIRPORT_CODE = AirportDAO.COL_AIRPORT_CODE;
    String COL_TERMINAL_CODE = TerminalDAO.COL_TERMINAL_CODE;
    String EXPLICIT_COL_GATE_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_GATE_ID);
    String EXPLICIT_COL_GATE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_GATE_CODE);
    String EXPLICIT_COL_AIRPORT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRPORT_CODE);
    String EXPLICIT_COL_TERMINAL_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TERMINAL_CODE);

    // DAO methods
}
