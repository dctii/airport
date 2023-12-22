package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.persistence.dao.BoardingPassDAO;
import com.solvd.airport.util.SQLUtils;

import java.sql.*;

public class BoardingPassDAOImpl implements BoardingPassDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_BOARDING_PASS_SQL =
            "INSERT INTO boarding_passes (boarding_time, boarding_group, check_in_id, flight_id, baggage_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

    @Override
    public void insertBoardingPass(BoardingPass boardingPass) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(INSERT_BOARDING_PASS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setTimestamp(1, boardingPass.getBoardingTime());
            statement.setString(2, boardingPass.getBoardingGroup());
            statement.setInt(3, boardingPass.getCheckInId());
            statement.setString(4, boardingPass.getFlightId());
            statement.setString(5, boardingPass.getBaggageId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating boarding pass failed, no rows affected.");
            }

            SQLUtils.setGeneratedKey(statement, boardingPass::setBoardingPassId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
