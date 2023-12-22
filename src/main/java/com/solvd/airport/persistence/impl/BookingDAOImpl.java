package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.dao.BookingDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDAOImpl implements BookingDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String FIND_BY_BOOKING_NUMBER_SQL =
            "SELECT * FROM bookings WHERE booking_number = ?";
    private static final String UPDATE_BOOKING_SQL =
            "UPDATE bookings SET flight_id = ?, booking_status = ? WHERE booking_number = ?";

    @Override
    public Booking findByBookingNumber(String bookingNumber) {
        Booking booking = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_BOOKING_NUMBER_SQL)
        ) {
            statement.setString(1, bookingNumber);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    booking = new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setBookingNumber(rs.getString("booking_number"));
                    booking.setPurchaseDatetime(rs.getTimestamp("purchase_datetime"));
                    booking.setSeatClass(rs.getString("seat_class"));
                    booking.setSeatNumber(rs.getString("seat_number"));
                    booking.setBookingStatus(rs.getString("booking_status"));
                    booking.setPrice(rs.getBigDecimal("price"));
                    booking.setPassengerId(rs.getInt("passenger_id"));
                    booking.setFlightId(rs.getString("flight_id"));
                    booking.setBookingAgencyId(rs.getInt("booking_agency_id"));
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
            ps.setString(1, booking.getFlightId());
            ps.setString(2, booking.getBookingStatus());
            ps.setString(3, booking.getBookingNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
