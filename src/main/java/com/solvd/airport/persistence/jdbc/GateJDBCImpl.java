package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GateJDBCImpl implements GateDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.GATE_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

            /*
            "INSERT INTO gates (gate_code, airport_code, terminal_code
                    VALUES (?, ?, ?)";
        */

    private static final List<Field<?>> INSERT_GATE_FIELDS = List.of(
            DSL.field(COL_GATE_CODE),
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_TERMINAL_CODE)
    );

    private static final String INSERT_GATE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_GATE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_GATE_FIELDS.size()))
            .getSQL();

    @Override
    public int create(Gate gateObj) {
        Connection conn = connectionPool.getConnection();

        int newGateId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(INSERT_GATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, gateObj.getGateCode());
            ps.setString(2, gateObj.getAirportCode());
            ps.setString(3, gateObj.getTerminalCode());

            SQLUtils.updateAndSetGeneratedId(ps, gateObj::setGateId);

            newGateId = gateObj.getGateId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newGateId;
    }

    /*
       "SELECT * FROM gates WHERE gate_id = ?";
    */
    private static final String SELECT_GATE_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_GATE_ID))
            .getSQL();

    @Override
    public Gate getById(int gateId) {
        Gate gate = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_GATE_BY_ID_SQL)
        ) {
            ps.setInt(1, gateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gate = extractGateFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return gate;
    }

    /*
    "SELECT * FROM gates WHERE gate_code = ? AND airport_code = ? AND terminal_code = ?";
*/
    private static final String SELECT_GATE_BY_CODES_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_GATE_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
            )
            .getSQL();

    public Gate getGateByCodes(String gateCode, String airportCode, String terminalCode) {
        Gate gate = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_GATE_BY_CODES_SQL)
        ) {
            ps.setString(1, gateCode);
            ps.setString(2, airportCode);
            ps.setString(3, terminalCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gate = extractGateFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return gate;
    }

    /*
    "UPDATE gates SET gate_code = ?, airport_code = ?, terminal_code = ?
            "WHERE gate_id = ?";
*/
    private static final String UPDATE_GATE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_GATE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRPORT_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TERMINAL_CODE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_GATE_ID))
            .getSQL();

    @Override
    public void update(Gate gateObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_GATE_SQL)
        ) {
            ps.setString(1, gateObj.getGateCode());
            ps.setString(2, gateObj.getAirportCode());
            ps.setString(3, gateObj.getTerminalCode());

            ps.setInt(4, gateObj.getGateId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "DELETE FROM gates WHERE gate_id = ?";
*/
    private static final String DELETE_GATE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_GATE_ID))
            .getSQL();

    @Override
    public void delete(int gateId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_GATE_SQL)
        ) {
            ps.setInt(1, gateId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    "DELETE FROM gates WHERE gate_code = ? AND airport_code = ? AND terminal_code = ?";
*/
    private static final String DELETE_GATE_BY_CODES_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_GATE_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
            )
            .getSQL();

    @Override
    public void deleteByCodes(String gateCode, String airportCode, String terminalCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_GATE_BY_CODES_SQL)
        ) {
            ps.setString(1, gateCode);
            ps.setString(2, airportCode);
            ps.setString(3, terminalCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "SELECT COUNT(*) FROM gates WHERE gate_code = ? AND airport_code = ?";
 */
    private static final String DOES_GATE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_GATE_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            )
            .getSQL();

    @Override
    public boolean doesGateExist(String gateCode, String airportCode) {
        boolean doesExist = false;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_GATE_EXIST_SQL)
        ) {
            ps.setString(1, gateCode);
            ps.setString(2, airportCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doesExist = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return doesExist;
    }

    /*
        SELECT f.*
        FROM flights f
        WHERE f.gate_id = #{gateId}
     */

    private static final String SELECT_FLIGHTS_BY_GATE_ID_SQL = create
            .select(DSL.field(FlightDAO.ALL_COLUMNS))
            .from(DSL.table(FlightDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(FlightDAO.EXPLICIT_COL_GATE_ID))
            .getSQL();

    @Override
    public Set<Flight> getFlightsByGateId(int gateId) {
        Set<Flight> flights = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_FLIGHTS_BY_GATE_ID_SQL)
        ) {
            ps.setInt(1, gateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Flight flight = extractFlightFromResultSet(rs);

                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return flights;
    }

    private Gate extractGateFromResultSet(ResultSet rs) {
        try {
            Gate gate = new Gate();
            gate.setGateId(rs.getInt(COL_GATE_ID));
            gate.setGateCode(rs.getString(COL_GATE_CODE));
            gate.setAirportCode(rs.getString(COL_AIRPORT_CODE));
            gate.setTerminalCode(rs.getString(COL_TERMINAL_CODE));

            Set<Flight> flights = getFlightsByGateId(gate.getGateId());
            gate.setFlights(flights);

            return gate;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Gate", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Gate" + e);
        }
    }

    private Flight extractFlightFromResultSet(ResultSet rs) {
        try {
            Flight flight = new Flight();
            flight.setFlightId(rs.getInt(FlightDAO.COL_FLIGHT_ID));
            flight.setFlightCode(rs.getString(FlightDAO.COL_FLIGHT_CODE));
            flight.setDepartureTime(rs.getTimestamp(FlightDAO.COL_DEPARTURE_TIME));
            flight.setArrivalTime(rs.getTimestamp(FlightDAO.COL_ARRIVAL_TIME));
            flight.setDestination(rs.getString(FlightDAO.COL_DESTINATION));
            flight.setAirlineCode(rs.getString(FlightDAO.COL_AIRLINE_CODE));
            flight.setGateId(rs.getInt(FlightDAO.COL_GATE_ID));
            flight.setAircraftModel(rs.getString(FlightDAO.COL_AIRCRAFT_MODEL));
            flight.setPassengerCapacity(rs.getInt(FlightDAO.COL_PASSENGER_CAPACITY));
            flight.setTailNumber(rs.getString(FlightDAO.COL_TAIL_NUMBER));
            return flight;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Flight", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Flight" + e);
        }
    }
}
