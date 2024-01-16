package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.FlightStaffMember;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.persistence.FlightStaffMemberDAO;
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

public class FlightJDBCImpl implements FlightDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.FLIGHT_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


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

    @Override
    public int create(Flight flightObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_FLIGHT_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, flightObj.getFlightCode());
            ps.setTimestamp(2, flightObj.getDepartureTime());
            ps.setTimestamp(3, flightObj.getArrivalTime());
            ps.setString(4, flightObj.getDestination());
            SQLUtils.setStringOrNull(ps, 5, flightObj.getAirlineCode());
            SQLUtils.setIntOrNull(ps, 6, flightObj.getGateId());
            SQLUtils.setStringOrNull(ps, 7, flightObj.getAircraftModel());
            SQLUtils.setIntOrNull(ps, 8, flightObj.getPassengerCapacity());
            SQLUtils.setStringOrNull(ps, 9, flightObj.getTailNumber());

            SQLUtils.updateAndSetGeneratedId(ps, flightObj::setFlightId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
        "SELECT * FROM flights WHERE flight_id = ?";
     */

    private static final String SELECT_FLIGHT_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_ID))
            .getSQL();

    @Override
    public Flight getById(int flightId) {
        Flight flight = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_FLIGHT_BY_ID_SQL)
        ) {
            ps.setInt(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = extractFlightFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return flight;
    }


    /*
        "SELECT * FROM flights WHERE flight_code = ?";
     */
    private static final String SELECT_FLIGHT_BY_CODE_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_CODE))
            .getSQL();

    @Override
    public Flight getFlightByCode(String flightCode) {
        Flight flight = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_FLIGHT_BY_CODE_SQL)
        ) {
            ps.setString(1, flightCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = extractFlightFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return flight;
    }

    /*
        "UPDATE flights SET flight_code = ?, departure_time = ?, arrival_time = ?, destination = ?, airline_code = ?, " +
                    "gate_id = ?, aircraft_model = ?, passenger_capacity = ?, tail_number = ? " +
                    "WHERE flight_id = ?";
     */

    private static final String UPDATE_FLIGHT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_FLIGHT_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_DEPARTURE_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ARRIVAL_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_DESTINATION), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_GATE_ID), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRCRAFT_MODEL), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSENGER_CAPACITY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TAIL_NUMBER), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_ID))
            .getSQL();


    @Override
    public void update(Flight flightObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_FLIGHT_SQL)
        ) {
            ps.setString(1, flightObj.getFlightCode());
            SQLUtils.setTimestampOrNull(ps, 2, flightObj.getDepartureTime());
            SQLUtils.setTimestampOrNull(ps, 3, flightObj.getArrivalTime());
            SQLUtils.setStringOrNull(ps, 4, flightObj.getDestination());
            SQLUtils.setStringOrNull(ps, 5, flightObj.getAirlineCode());
            SQLUtils.setIntOrNull(ps, 6, flightObj.getGateId());
            SQLUtils.setStringOrNull(ps, 7, flightObj.getAircraftModel());
            SQLUtils.setIntOrNull(ps, 8, flightObj.getPassengerCapacity());
            SQLUtils.setStringOrNull(ps, 9, flightObj.getTailNumber());

            ps.setInt(10, flightObj.getFlightId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "UPDATE flights SET departure_time = ?, arrival_time = ?, destination = ?, airline_code = ?, " +
                    "gate_id = ?, aircraft_model = ?, passenger_capacity = ?, tail_number = ? " +
                    "WHERE flight_code = ?";
     */

    private static final String UPDATE_FLIGHT_BY_CODE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_DEPARTURE_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ARRIVAL_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_DESTINATION), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_GATE_ID), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRCRAFT_MODEL), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSENGER_CAPACITY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TAIL_NUMBER), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_CODE))
            .getSQL();

    @Override
    public void updateFlightByCode(Flight flightObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_FLIGHT_BY_CODE_SQL)
        ) {
            SQLUtils.setTimestampOrNull(ps, 1, flightObj.getDepartureTime());
            SQLUtils.setTimestampOrNull(ps, 2, flightObj.getArrivalTime());
            SQLUtils.setStringOrNull(ps, 3, flightObj.getDestination());
            SQLUtils.setStringOrNull(ps, 4, flightObj.getAirlineCode());
            SQLUtils.setIntOrNull(ps, 5, flightObj.getGateId());
            SQLUtils.setStringOrNull(ps, 6, flightObj.getAircraftModel());
            SQLUtils.setIntOrNull(ps, 7, flightObj.getPassengerCapacity());
            SQLUtils.setStringOrNull(ps, 8, flightObj.getTailNumber());

            ps.setString(9, flightObj.getFlightCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

        /*
            "DELETE FROM flights WHERE flight_id = ?";
        */

    private static final String DELETE_FLIGHT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_ID))
            .getSQL();

    @Override
    public void delete(int flightId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_FLIGHT_SQL)
        ) {
            ps.setInt(1, flightId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM flights WHERE flight_code = ?";
     */

    private static final String DELETE_FLIGHT_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_CODE))
            .getSQL();

    @Override
    public void deleteFlightByCode(String flightCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_FLIGHT_BY_CODE_SQL)
        ) {
            ps.setString(1, flightCode);
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
    private static final String DOES_FLIGHT_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_FLIGHT_CODE))
            .getSQL();

    @Override
    public boolean doesFlightExist(String flightCode) {
        boolean doesExist = false;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_FLIGHT_EXIST_SQL)
        ) {
            ps.setString(1, flightCode);
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
        SELECT fs.flight_staff_id, fs.flight_crew_id, fs.airline_staff_id
        FROM flight_staff fs
        JOIN flight_crew fc
        ON fs.flight_crew_id = fc.flight_crew_id
        WHERE fc.flight_code = #{flightCode}
     */

    private static final String SELECT_FLIGHT_CREW_BY_FLIGHT_CODE_SQL = create
            .select()
            .from(DSL.table(FlightStaffMemberDAO.TABLE_NAME))
            .join(DSL.table(FlightStaffMemberDAO.FLIGHT_CREW_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            FlightStaffMemberDAO.EXPLICIT_COL_FLIGHT_CREW_ID,
                            FlightStaffMemberDAO.FC_EXPLICIT_COL_FLIGHT_CREW_ID
                    )
            )
            .where(SQLUtils.eqPlaceholder(FlightStaffMemberDAO.FC_EXPLICIT_COL_FLIGHT_CODE))
            .getSQL();

    @Override
    public Set<FlightStaffMember> getFlightCrewByFlightCode(String flightCode) {
        Set<FlightStaffMember> flightCrew = new HashSet<>();
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_FLIGHT_CREW_BY_FLIGHT_CODE_SQL)
        ) {
            ps.setString(1, flightCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightStaffMember member = extractFlightStaffMemberFromResultSet(rs);

                    flightCrew.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return flightCrew;
    }

    private Flight extractFlightFromResultSet(ResultSet rs) {
        try {
            Flight flight = new Flight();
            flight.setFlightId(rs.getInt(COL_FLIGHT_ID));
            flight.setFlightCode(rs.getString(COL_FLIGHT_CODE));
            flight.setDepartureTime(rs.getTimestamp(COL_DEPARTURE_TIME));
            flight.setArrivalTime(rs.getTimestamp(COL_ARRIVAL_TIME));
            flight.setDestination(rs.getString(COL_DESTINATION));
            flight.setAirlineCode(rs.getString(COL_AIRLINE_CODE));
            flight.setGateId(rs.getInt(COL_GATE_ID));
            flight.setAircraftModel(rs.getString(COL_AIRCRAFT_MODEL));
            flight.setPassengerCapacity(rs.getInt(COL_PASSENGER_CAPACITY));
            flight.setTailNumber(rs.getString(COL_TAIL_NUMBER));

            Set<FlightStaffMember> flightCrew = getFlightCrewByFlightCode(flight.getFlightCode());
            flight.setFlightCrew(flightCrew);

            return flight;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Flight", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Flight" + e);
        }
    }

    private FlightStaffMember extractFlightStaffMemberFromResultSet(ResultSet rs) {
        try {
            FlightStaffMember member = new FlightStaffMember();
            member.setFlightStaffId(rs.getInt(FlightStaffMemberDAO.COL_FLIGHT_STAFF_ID));
            member.setFlightCrewId(rs.getInt(FlightStaffMemberDAO.COL_FLIGHT_CREW_ID));
            member.setAirlineStaffId(rs.getInt(FlightStaffMemberDAO.COL_AIRLINE_STAFF_ID));


            return member;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Baggage", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Baggage" + e);
        }
    }


}
