package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CheckInDAO;
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
import java.util.List;

public class CheckInJDBCImpl implements CheckInDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.CHECK_IN_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        "INSERT INTO check_ins (check_in_method, pass_issued, airline_staff_id, booking_id) VALUES (?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_CHECK_IN_FIELDS = List.of(
            DSL.field(COL_CHECK_IN_METHOD),
            DSL.field(COL_PASS_ISSUED),
            DSL.field(COL_AIRLINE_STAFF_ID),
            DSL.field(COL_BOOKING_ID)
    );

    private static final String INSERT_CHECK_IN_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_CHECK_IN_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_CHECK_IN_FIELDS.size()))
            .getSQL();

    @Override
    public int create(CheckIn checkInObj) {
        Connection conn = connectionPool.getConnection();

        int newCheckInId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(INSERT_CHECK_IN_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            SQLUtils.setStringOrNull(ps, 1, checkInObj.getCheckInMethod());
            SQLUtils.setBooleanOrFalse(ps, 2, checkInObj.isPassIssued());
            SQLUtils.setIntOrNull(ps, 3, checkInObj.getAirlineStaffId());
            SQLUtils.setIntOrNull(ps, 4, checkInObj.getBookingId());

            SQLUtils.updateAndSetGeneratedId(ps, checkInObj::setCheckInId);

            newCheckInId = checkInObj.getCheckInId();
        } catch (SQLException e) {
            LOGGER.error("Error creating CheckIn: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newCheckInId;
    }


    /*
        "SELECT * FROM check_ins WHERE check_in_id = ?";
     */

    private static final String SELECT_CHECK_IN_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();

    @Override
    public CheckIn getById(int checkInId) {
        CheckIn checkIn = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_CHECK_IN_BY_ID_SQL)
        ) {
            ps.setInt(1, checkInId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkIn = extractCheckInFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting CheckIn by ID: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return checkIn;
    }


    /*
        "SELECT check_ins.* FROM check_ins JOIN bookings ON check_ins.booking_id = bookings.booking_id WHERE bookings.booking_number = ?";
     */

    private static final String SELECT_CHECK_IN_BY_BOOKING_NUMBER_SQL = create
            .select(DSL.field(ALL_COLUMNS))
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(BookingDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            EXPLICIT_COL_BOOKING_ID,
                            BookingDAO.EXPLICIT_COL_BOOKING_ID)

            )
            .where(SQLUtils.eqPlaceholder(BookingDAO.COL_BOOKING_NUMBER))
            .getSQL();

    @Override
    public CheckIn getCheckInByBookingNumber(String bookingNumber) {
        CheckIn checkIn = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_CHECK_IN_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkIn = extractCheckInFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting CheckIn by Booking Number: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return checkIn;
    }

    /*
        "UPDATE check_ins SET pass_issued = ? WHERE check_in_id = ?";
     */

    private static final String UPDATE_CHECK_IN_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_CHECK_IN_METHOD), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASS_ISSUED), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_STAFF_ID), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_BOOKING_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();


    @Override
    public void update(CheckIn checkInObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_CHECK_IN_SQL)
        ) {
            SQLUtils.setStringOrNull(ps, 1, checkInObj.getCheckInMethod());
            SQLUtils.setBooleanOrFalse(ps, 2, checkInObj.isPassIssued());
            SQLUtils.setIntOrNull(ps, 3, checkInObj.getAirlineStaffId());
            SQLUtils.setIntOrNull(ps, 4, checkInObj.getBookingId());

            ps.setInt(5, checkInObj.getCheckInId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating CheckIn: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM check_ins WHERE check_in_id = ?";
     */

    private static final String DELETE_CHECK_IN_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();

    @Override
    public void delete(int checkInId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_CHECK_IN_SQL)
        ) {
            ps.setInt(1, checkInId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting CheckIn: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "SELECT COUNT(*) FROM check_ins WHERE booking_id = ?";
 */
    private static final String HAS_CHECK_IN_FOR_BOOKING_ID_SQL = create
            .select(DSL.count())
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

    @Override
    public boolean hasCheckInForBookingId(int bookingId) {
        boolean hasCheckIn = false;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(HAS_CHECK_IN_FOR_BOOKING_ID_SQL)
        ) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasCheckIn = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking if CheckIn exists for Booking ID: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return hasCheckIn;
    }

    private CheckIn extractCheckInFromResultSet(ResultSet rs) {
        try {
            CheckIn checkIn = new CheckIn();
            checkIn.setCheckInId(rs.getInt(COL_CHECK_IN_ID));
            checkIn.setCheckInMethod(rs.getString(COL_CHECK_IN_METHOD));
            checkIn.setPassIssued(rs.getBoolean(COL_PASS_ISSUED));
            checkIn.setAirlineStaffId(rs.getInt(COL_AIRLINE_STAFF_ID));
            checkIn.setBookingId(rs.getInt(COL_BOOKING_ID));
            return checkIn;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of CheckIn", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of CheckIn" + e);
        }
    }
}
