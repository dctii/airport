package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.mappers.BookingDAO;
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
    public void createBooking(Booking booking) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_BOOKING_SQL)) {
            ps.setString(1, booking.getBookingNumber());
            ps.setTimestamp(2, booking.getPurchaseDatetime());
            ps.setString(3, booking.getSeatClass());
            ps.setString(4, booking.getSeatNumber());
            ps.setString(5, booking.getStatus());
            ps.setBigDecimal(6, booking.getPrice());
            ps.setString(7, booking.getAgency());
            ps.setString(8, booking.getPassportNumber());
            ps.setString(9, booking.getFlightCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking getBookingById(int id) {
        Booking booking = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, id);
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
    public void updateBooking(Booking booking) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BOOKING_SQL)) {
            ps.setString(1, booking.getSeatClass());
            ps.setString(2, booking.getSeatNumber());
            ps.setString(3, booking.getStatus());
            ps.setBigDecimal(4, booking.getPrice());
            ps.setString(5, booking.getAgency());
            ps.setString(6, booking.getPassportNumber());
            ps.setString(7, booking.getFlightCode());
            ps.setInt(8, booking.getBookingId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooking(int id) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BOOKING_SQL)) {
            ps.setInt(1, id);
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
