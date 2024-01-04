package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createBooking(Booking bookingObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_BOOKING_SQL)
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

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Booking findByBookingNumber(String bookingNumber) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_BOOKING_NUMBER_SQL)
        ) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateBooking(Booking bookingObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_SQL)
        ) {
            ps.setString(1, bookingObj.getSeatClass());
            ps.setString(2, bookingObj.getSeatNumber());
            ps.setString(3, bookingObj.getStatus());
            ps.setBigDecimal(4, bookingObj.getPrice());
            ps.setString(5, bookingObj.getAgency());
            ps.setString(6, bookingObj.getPassportNumber());
            ps.setString(7, bookingObj.getFlightCode());
            ps.setInt(8, bookingObj.getBookingId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_BOOKING_SQL)
        ) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
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
    }

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
            .insertInto(DSL.table(TABLE_NAME))
            .values(SQLUtils.createPlaceholders(INSERT_BOOKING_FIELDS.size()))
            .getSQL();


    /*
        "SELECT * FROM bookings WHERE booking_id = ?";
    */

    private static final String FIND_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

    /*
        "SELECT * FROM bookings WHERE booking_number = ?";
    */
    private static final String FIND_BY_BOOKING_NUMBER_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_NUMBER))
            .getSQL();

    /*
        "UPDATE bookings SET seat_class = ?, seat_number = ?, status = ?, price = ?, agency = ?, passport_number = ?, flight_code = ? WHERE booking_id = ?";
    */

    private static final String UPDATE_BOOKING_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_SEAT_CLASS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEAT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_STATUS), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AGENCY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PASSPORT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_FLIGHT_CODE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

    /*
        "DELETE FROM bookings WHERE booking_id = ?";
    */

    private static final String DELETE_BOOKING_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOOKING_ID))
            .getSQL();

}
