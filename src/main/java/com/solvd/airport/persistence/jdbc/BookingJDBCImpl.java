package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.BookingDAO;
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

public class BookingJDBCImpl implements BookingDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOOKING_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

    /*
        "INSERT INTO bookings (booking_number, purchase_datetime, seat_class,
        seat_number, status, price, agency, passport_number, flight_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_BOOKING_FIELDS = List.of(
            DSL.field(COL_BOOKING_NUMBER),
            DSL.field(COL_PURCHASE_DATETIME),
            DSL.field(COL_SEAT_CLASS),
            DSL.field(COL_SEAT_NUMBER),
            DSL.field(COL_STATUS),
            DSL.field(COL_PRICE),
            DSL.field(COL_AGENCY),
            DSL.field(COL_PASSPORT_NUMBER),
            DSL.field(COL_FLIGHT_CODE)
    );

    private static final String INSERT_BOOKING_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_BOOKING_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_BOOKING_FIELDS.size()))
            .getSQL();

    @Override
    public int create(Booking bookingObj) {
        Connection conn = connectionPool.getConnection();

        int newBookingId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_BOOKING_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, bookingObj.getBookingNumber());
            ps.setTimestamp(2, bookingObj.getPurchaseDatetime());
            ps.setString(3, bookingObj.getSeatClass());
            ps.setString(4, bookingObj.getSeatNumber());
            ps.setString(5, bookingObj.getStatus());
            ps.setBigDecimal(6, bookingObj.getPrice());
            ps.setString(7, bookingObj.getAgency());
            ps.setString(8, bookingObj.getPassportNumber());
            ps.setString(9, bookingObj.getFlightCode());

            SQLUtils.updateAndSetGeneratedId(ps, bookingObj::setBookingId);

            newBookingId = bookingObj.getBookingId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newBookingId;
    }

        /*
        "SELECT * FROM bookings WHERE booking_id = ?";
    */

    private static final String SELECT_BOOKING_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

    @Override
    public Booking getById(int bookingId) {
        Booking booking = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BOOKING_BY_ID_SQL)
        ) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return booking;
    }

    /*
    "SELECT * FROM bookings WHERE booking_number = ?";
*/
    private static final String SELECT_BOOKING_BY_BOOKING_NUMBER_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_NUMBER))
            .getSQL();

    @Override
    public Booking getBookingByBookingNumber(String bookingNumber) {
        Booking booking = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BOOKING_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return booking;
    }

        /*
        "UPDATE bookings
        SET
            booking_number = ?,
            purchase_datetime = ?,
            seat_class = ?,
            seat_number = ?,
            status = ?,
            price = ?,
            agency = ?,
            passport_number = ?,
            flight_code = ?
        WHERE booking_id = ?";
    */

    private static final String UPDATE_BOOKING_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_BOOKING_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PURCHASE_DATETIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEAT_CLASS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEAT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_STATUS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AGENCY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSPORT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_FLIGHT_CODE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();


    @Override
    public void update(Booking bookingObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_SQL)
        ) {
            ps.setString(1, bookingObj.getBookingNumber());
            ps.setTimestamp(2, bookingObj.getPurchaseDatetime());
            ps.setString(3, bookingObj.getSeatClass());
            SQLUtils.setStringOrNull(ps, 4, bookingObj.getSeatNumber());
            ps.setString(5, bookingObj.getStatus());
            ps.setBigDecimal(6, bookingObj.getPrice());
            SQLUtils.setStringOrNull(ps, 7, bookingObj.getAgency());
            ps.setString(8, bookingObj.getPassportNumber());
            ps.setString(9, bookingObj.getFlightCode());

            ps.setInt(10, bookingObj.getBookingId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

            /*
        "UPDATE bookings
        SET
            booking_number = ?,
            purchase_datetime = ?,
            seat_class = ?,
            seat_number = ?,
            status = ?,
            price = ?,
            agency = ?,
            passport_number = ?,
            flight_code = ?
        WHERE booking_id = ?";
    */

    private static final String UPDATE_BOOKING_BY_BOOKING_NUMBER_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PURCHASE_DATETIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEAT_CLASS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEAT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_STATUS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AGENCY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSPORT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_FLIGHT_CODE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_NUMBER))
            .getSQL();

    @Override
    public void updateBookingByBookingNumber(Booking bookingObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setTimestamp(1, bookingObj.getPurchaseDatetime());
            ps.setString(2, bookingObj.getSeatClass());
            SQLUtils.setStringOrNull(ps, 3, bookingObj.getSeatNumber());
            ps.setString(4, bookingObj.getStatus());
            ps.setBigDecimal(5, bookingObj.getPrice());
            SQLUtils.setStringOrNull(ps, 6, bookingObj.getAgency());
            ps.setString(7, bookingObj.getPassportNumber());
            ps.setString(8, bookingObj.getFlightCode());

            ps.setString(9, bookingObj.getBookingNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM bookings WHERE booking_id = ?";
    */

    private static final String DELETE_BOOKING_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

    @Override
    public void delete(int bookingId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_BOOKING_SQL)
        ) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM bookings WHERE booking_number = ?";
    */
    private static final String DELETE_BOOKING_BY_BOOKING_NUMBER_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_NUMBER))
            .getSQL();

    @Override
    public void deleteBookingByBookingNumber(String bookingNumber) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_BOOKING_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setString(1, bookingNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT COUNT(*) > 0 FROM bookings
        WHERE booking_number = #{bookingNumber}
     */
    private static final String DOES_BOOKING_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_NUMBER))
            .getSQL();

    @Override
    public boolean doesBookingExist(String bookingNumber) {
        boolean doesExist = false;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_BOOKING_EXIST_SQL)
        ) {
            ps.setString(1, bookingNumber);
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

    private Booking extractBookingFromResultSet(ResultSet rs) {
        try {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt(COL_BOOKING_ID));
            booking.setBookingNumber(rs.getString(COL_BOOKING_NUMBER));
            booking.setPurchaseDatetime(rs.getTimestamp(COL_PURCHASE_DATETIME));
            booking.setSeatClass(rs.getString(COL_SEAT_CLASS));
            booking.setSeatNumber(rs.getString(COL_SEAT_NUMBER));
            booking.setStatus(rs.getString(COL_STATUS));
            booking.setPrice(rs.getBigDecimal(COL_PRICE));
            booking.setAgency(rs.getString(COL_AGENCY));
            booking.setPassportNumber(rs.getString(COL_PASSPORT_NUMBER));
            booking.setFlightCode(rs.getString(COL_FLIGHT_CODE));
            return booking;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Booking", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Booking" + e);
        }
    }


}
