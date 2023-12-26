package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.mappers.CheckInDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CheckInDAOImpl implements CheckInDAO {
    private static final Logger LOGGER = LogManager.getLogger(CheckInDAOImpl.class);
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_CHECK_IN_SQL =
            "INSERT INTO check_ins (check_in_method, pass_issued, airline_staff_id, booking_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM check_ins WHERE check_in_id = ?";
    private static final String FIND_BY_BOOKING_NUMBER_SQL =
            "SELECT check_ins.* FROM check_ins JOIN bookings ON check_ins.booking_id = bookings.booking_id WHERE bookings.booking_number = ?";
    private static final String UPDATE_CHECK_IN_SQL =
            "UPDATE check_ins SET pass_issued = ? WHERE check_in_id = ?";
    private static final String DELETE_CHECK_IN_SQL =
            "DELETE FROM check_ins WHERE check_in_id = ?";

    private static final String CHECK_IF_CHECK_IN_EXISTS_SQL =
            "SELECT COUNT(*) FROM check_ins WHERE booking_id = ?";

    @Override
    public void createCheckIn(CheckIn checkIn) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_CHECK_IN_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, checkIn.getCheckInMethod());
            ps.setBoolean(2, checkIn.isPassIssued());
            ps.setInt(3, checkIn.getAirlineStaffId());
            ps.setInt(4, checkIn.getBookingId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating check-in failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    checkIn.setCheckInId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating check-in failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error creating CheckIn: ", e);
        }
    }

    @Override
    public CheckIn getCheckInById(int id) {
        CheckIn checkIn = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, id);
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
    public void updateCheckIn(CheckIn checkIn) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_CHECK_IN_SQL)
        ) {
            ps.setBoolean(1, checkIn.isPassIssued());
            ps.setInt(2, checkIn.getCheckInId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating CheckIn: ", e);
        }
    }

    @Override
    public void deleteCheckIn(int id) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_CHECK_IN_SQL)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting CheckIn: ", e);
        }
    }

    @Override
    public boolean hasCheckInForBookingId(int bookingId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(CHECK_IF_CHECK_IN_EXISTS_SQL)
        ) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error checking if CheckIn exists for Booking ID: ", e);
        }
        return false;
    }

    private CheckIn extractCheckInFromResultSet(ResultSet rs) throws SQLException {
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInId(rs.getInt("check_in_id"));
        checkIn.setCheckInMethod(rs.getString("check_in_method"));
        checkIn.setPassIssued(rs.getBoolean("pass_issued"));
        checkIn.setAirlineStaffId(rs.getInt("airline_staff_id"));
        checkIn.setBookingId(rs.getInt("booking_id"));
        return checkIn;
    }
}
