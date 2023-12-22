package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.dao.CheckInDAO;
import com.solvd.airport.util.SQLKeys;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CheckInDAOImpl implements CheckInDAO {
    private static final Logger LOGGER = LogManager.getLogger(CheckInDAOImpl.class);
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_CHECK_IN_SQL =
            SQLKeys.INSERT_INTO + " check_ins (check_in_method, airport_staff_id, booking_id) "
                    + SQLKeys.VALUES + " (?, ?, ?)";
    private static final String UPDATE_CHECK_IN_SQL =
            SQLKeys.UPDATE + " check_ins "
                    + SQLKeys.SET + " pass_issued = ? "
                    + SQLKeys.WHERE + " booking_id = ?";

    @Override
    public void insertCheckIn(CheckIn checkIn) {
        ResultSet generatedKeys = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(INSERT_CHECK_IN_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, checkIn.getCheckInMethod());
            statement.setInt(2, checkIn.getAirportStaffId());
            statement.setInt(3, checkIn.getBookingId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating check-in failed, no rows affected.");
            }

            // need to set the AUTO_INCREMENT `*_id`
            SQLUtils.setGeneratedKey(statement, checkIn::setCheckInId);

        } catch (SQLException e) {
            LOGGER.error("Insert CheckIn - Error: ", e);
        }
    }


    @Override
    public void updateCheckIn(CheckIn checkIn) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(UPDATE_CHECK_IN_SQL)
        ) {
            statement.setBoolean(1, checkIn.isPassIssued());
            statement.setInt(2, checkIn.getBookingId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Update CheckIn - Error: ", e);
        }
    }
}

