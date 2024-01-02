package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class BookingDAOImpl implements BookingDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO bookings (booking_number, purchase_datetime, seat_class, seat_number, status, price, agency, passport_number, flight_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM bookings WHERE booking_id = ?";
    private static final String FIND_BY_BOOKING_NUMBER_SQL =
            "SELECT * FROM bookings WHERE booking_number = ?";
    private static final String UPDATE_BOOKING_SQL =
            "UPDATE bookings SET seat_class = ?, seat_number = ?, status = ?, price = ?, agency = ?, passport_number = ?, flight_code = ? WHERE booking_id = ?";
    private static final String DELETE_BOOKING_SQL =
            "DELETE FROM bookings WHERE booking_id = ?";

    @Override
    public void createBooking(Booking bookingObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_BOOKING_SQL)) {
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
        Booking booking = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public Booking findByBookingNumber(String bookingNumber) {
        Booking booking = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_BOOKING_NUMBER_SQL)) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = extractBookingFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public void updateBooking(Booking bookingObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_SQL)) {
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
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BOOKING_SQL)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setBookingNumber(rs.getString("booking_number"));
        booking.setPurchaseDatetime(rs.getTimestamp("purchase_datetime"));
        booking.setSeatClass(rs.getString("seat_class"));
        booking.setSeatNumber(rs.getString("seat_number"));
        booking.setStatus(rs.getString("status"));
        booking.setPrice(rs.getBigDecimal("price"));
        booking.setAgency(rs.getString("agency"));
        booking.setPassportNumber(rs.getString("passport_number"));
        booking.setFlightCode(rs.getString("flight_code"));
        return booking;
    }
}
