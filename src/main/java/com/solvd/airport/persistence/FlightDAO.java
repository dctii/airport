package com.solvd.airport.persistence;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.FlightStaffMember;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface FlightDAO extends AbstractDAO<Flight> {
    int create(@Param("flightObj") Flight flightObj);

    Flight getById(@Param("flightId") int flightId);

    Flight getFlightByCode(@Param("flightCode") String flightCode);

    void update(@Param("flightObj") Flight flightObj);

    void updateFlightByCode(@Param("flightObj") Flight flightObj);

    void delete(@Param("flightId") int flightId);

    void deleteFlightByCode(@Param("flightCode") String flightCode);

    boolean doesFlightExist(@Param("flightCode") String flightCode);

    Set<FlightStaffMember> getFlightCrewByFlightCode(@Param("flightCode") String flightCode);

    String TABLE_NAME = "flights";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_FLIGHT_ID = "flight_id";
    String COL_FLIGHT_CODE = "flight_code";
    String COL_DEPARTURE_TIME = "departure_time";
    String COL_ARRIVAL_TIME = "arrival_time";
    String COL_DESTINATION = "destination";
    String COL_AIRLINE_CODE = AirlineDAO.COL_AIRLINE_CODE;
    String COL_GATE_ID = GateDAO.COL_GATE_ID;
    String COL_AIRCRAFT_MODEL = "aircraft_model";
    String COL_PASSENGER_CAPACITY = "passenger_capacity";
    String COL_TAIL_NUMBER = "tail_number";
    String EXPLICIT_COL_FLIGHT_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_FLIGHT_CODE);
    String EXPLICIT_COL_DEPARTURE_TIME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_DEPARTURE_TIME);
    String EXPLICIT_COL_ARRIVAL_TIME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ARRIVAL_TIME);
    String EXPLICIT_COL_DESTINATION = SQLUtils.qualifyColumnName(TABLE_NAME, COL_DESTINATION);
    String EXPLICIT_COL_AIRLINE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_CODE);
    String EXPLICIT_COL_GATE_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_GATE_ID);
    String EXPLICIT_COL_AIRCRAFT_MODEL = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRCRAFT_MODEL);
    String EXPLICIT_COL_PASSENGER_CAPACITY = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PASSENGER_CAPACITY);
    String EXPLICIT_COL_TAIL_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TAIL_NUMBER);
}
