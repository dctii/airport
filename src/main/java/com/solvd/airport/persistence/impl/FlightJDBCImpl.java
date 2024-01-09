package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Flight;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FlightDAOImpl implements FlightDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createFlight(Flight flightObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_FLIGHT_SQL)
        ) {
            ps.setString(1, flightObj.getFlightCode());
            ps.setTimestamp(2, flightObj.getDepartureTime());
            ps.setTimestamp(3, flightObj.getArrivalTime());
            ps.setString(4, flightObj.getDestination());
            ps.setString(5, flightObj.getAirlineCode());
            ps.setInt(6, flightObj.getGateId());
            ps.setString(7, flightObj.getAircraftModel());
            ps.setInt(8, flightObj.getPassengerCapacity());
            ps.setString(9, flightObj.getTailNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flight getFlightByCode(String flightCode) {
        Flight flight = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CODE_SQL)
        ) {
            ps.setString(1, flightCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = extractFlightFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }


    @Override
    public void updateFlight(Flight flightObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_FLIGHT_SQL)
        ) {
            ps.setTimestamp(1, flightObj.getDepartureTime());
            ps.setTimestamp(2, flightObj.getArrivalTime());
            ps.setString(3, flightObj.getDestination());
            ps.setString(4, flightObj.getAirlineCode());
            ps.setInt(5, flightObj.getGateId());
            ps.setString(6, flightObj.getAircraftModel());
            ps.setInt(7, flightObj.getPassengerCapacity());
            ps.setString(8, flightObj.getTailNumber());
            ps.setString(9, flightObj.getFlightCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFlight(String flightCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_FLIGHT_SQL)
        ) {
            ps.setString(1, flightCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Flight extractFlightFromResultSet(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setFlightCode(rs.getString(COL_FLIGHT_CODE));
        flight.setDepartureTime(rs.getTimestamp(COL_DEPARTURE_TIME));
        flight.setArrivalTime(rs.getTimestamp(COL_ARRIVAL_TIME));
        flight.setDestination(rs.getString(COL_DESTINATION));
        flight.setAirlineCode(rs.getString(COL_AIRLINE_CODE));
        flight.setGateId(rs.getInt(COL_GATE_ID));
        flight.setAircraftModel(rs.getString(COL_AIRCRAFT_MODEL));
        flight.setPassengerCapacity(rs.getInt(COL_PASSENGER_CAPACITY));
        flight.setTailNumber(rs.getString(COL_TAIL_NUMBER));

        return flight;
    }

        /*
        "INSERT INTO flights (flight_code, departure_time, arrival_time, destination, " +
                    "airline_code, gate_id, aircraft_model, passenger_capacity, tail_number) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_FLIGHT_FIELDS = List.of(
            DSL.field(COL_FLIGHT_CODE),
            DSL.field(COL_DEPARTURE_TIME),
            DSL.field(COL_ARRIVAL_TIME),
            DSL.field(COL_DESTINATION),
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_GATE_ID),
            DSL.field(COL_AIRCRAFT_MODEL),
            DSL.field(COL_PASSENGER_CAPACITY),
            DSL.field(COL_TAIL_NUMBER)
    );

    private static final String INSERT_FLIGHT_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_FLIGHT_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_FLIGHT_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM flights WHERE flight_code = ?";
     */

    private static final String FIND_BY_CODE_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_CODE))
            .getSQL();

    /*
        "UPDATE flights SET departure_time = ?, arrival_time = ?, destination = ?, airline_code = ?, " +
                    "gate_id = ?, aircraft_model = ?, passenger_capacity = ?, tail_number = ? " +
                    "WHERE flight_code = ?";
     */

    private static final String UPDATE_FLIGHT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_DEPARTURE_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ARRIVAL_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_DESTINATION), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_GATE_ID), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRCRAFT_MODEL), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSENGER_CAPACITY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TAIL_NUMBER), SQLConstants.PLACEHOLDER)
            .where(COL_FLIGHT_CODE)
            .getSQL();

    /*
        "DELETE FROM flights WHERE flight_code = ?";
     */

    private static final String DELETE_FLIGHT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(COL_FLIGHT_CODE)
            .getSQL();
}
