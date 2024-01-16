package com.solvd.airport.persistence;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface GateDAO extends AbstractDAO<Gate> {

    int create(@Param("gateObj") Gate gateObj);

    Gate getById(@Param("gateObj") int gateId);

    Gate getGateByCodes(
            @Param("gateCode") String gateCode,
            @Param("airportCode") String airportCode,
            @Param("terminalCode") String terminalCode
    );

    void update(@Param("gateObj") Gate gateObj);

    void delete(@Param("gateId") int gateId);

    void deleteByCodes(
            @Param("gateCode") String gateCode,
            @Param("airportCode") String airportCode,
            @Param("terminalCode") String terminalCode
    );

    boolean doesGateExist(
            @Param("gateCode") String gateCode,
            @Param("airportCode") String airportCode
    );

    Set<Flight> getFlightsByGateId(@Param("gateId") int gateId);

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
}
