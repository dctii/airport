package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class CheckInDAOImpl implements CheckInDAO {
    private static final Logger LOGGER = LogManager.getLogger(CheckInDAOImpl.class);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createCheckIn(CheckIn checkInObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_CHECK_IN_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, checkInObj.getCheckInMethod());
            ps.setBoolean(2, checkInObj.isPassIssued());
            ps.setInt(3, checkInObj.getAirlineStaffId());
            ps.setInt(4, checkInObj.getBookingId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating check-in failed, no rows affected.");
            }

            SQLUtils.setGeneratedKey(ps, checkInObj::setCheckInId);
        } catch (SQLException e) {
            LOGGER.error("Error creating CheckIn: ", e);
        }
    }

    @Override
    public CheckIn getCheckInById(int checkInId) {
        CheckIn checkIn = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, checkInId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkIn = extractCheckInFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting CheckIn by ID: ", e);
        }
        return checkIn;
    }

    @Override
    public CheckIn getCheckInByBookingNumber(String bookingNumber) {
        CheckIn checkIn = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    checkIn = extractCheckInFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting CheckIn by Booking Number: ", e);
        }
        return checkIn;
    }

    @Override
    public void updateCheckIn(CheckIn checkInObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_CHECK_IN_SQL)
        ) {
            ps.setBoolean(1, checkInObj.isPassIssued());
            ps.setInt(2, checkInObj.getCheckInId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating CheckIn: ", e);
        }
    }

    @Override
    public void deleteCheckIn(int checkInId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_CHECK_IN_SQL)
        ) {
            ps.setInt(1, checkInId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting CheckIn: ", e);
        }
    }

    @Override
    public boolean hasCheckInForBookingId(int bookingId) {
        boolean hasCheckIn = false;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(CHECK_IF_CHECK_IN_EXISTS_SQL)
        ) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasCheckIn = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking if CheckIn exists for Booking ID: ", e);
        }
        return hasCheckIn;
    }

    private CheckIn extractCheckInFromResultSet(ResultSet rs) throws SQLException {
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInId(rs.getInt(COL_CHECK_IN_ID));
        checkIn.setCheckInMethod(rs.getString(COL_CHECK_IN_METHOD));
        checkIn.setPassIssued(rs.getBoolean(COL_PASS_ISSUED));
        checkIn.setAirlineStaffId(rs.getInt(COL_AIRLINE_STAFF_ID));
        checkIn.setBookingId(rs.getInt(COL_BOOKING_ID));
        return checkIn;
    }

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

    /*
        "SELECT * FROM check_ins WHERE check_in_id = ?";
     */

    private static final String FIND_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();

    /*
        "SELECT check_ins.* FROM check_ins JOIN bookings ON check_ins.booking_id = bookings.booking_id WHERE bookings.booking_number = ?";
     */

    private static final String FIND_BY_BOOKING_NUMBER_SQL = create
            .select(DSL.field(ALL_COLUMNS))
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(BookingDAO.TABLE_NAME))
            .on(DSL.field(EXPLICIT_COL_BOOKING_ID).eq(DSL.field(BookingDAO.EXPLICIT_COL_BOOKING_ID)))
            .where(SQLUtils.eqPlaceholder(BookingDAO.COL_BOOKING_NUMBER))
            .getSQL();

    /*
        "UPDATE check_ins SET pass_issued = ? WHERE check_in_id = ?";
     */

    private static final String UPDATE_CHECK_IN_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PASS_ISSUED), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();

    /*
        "DELETE FROM check_ins WHERE check_in_id = ?";
     */

    private static final String DELETE_CHECK_IN_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();


    /*
        "SELECT COUNT(*) FROM check_ins WHERE booking_id = ?";
     */

    private static final String CHECK_IF_CHECK_IN_EXISTS_SQL = create
            .select(DSL.count())
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();
}
