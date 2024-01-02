package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class BoardingPassDAOImpl implements BoardingPassDAO {
    private static final Logger LOGGER = LogManager.getLogger(BoardingPassDAOImpl.class);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_BOARDING_PASS_SQL =
            "INSERT INTO boarding_passes (is_boarded, boarding_time, boarding_group, check_in_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM boarding_passes WHERE boarding_pass_id = ?";
    private static final String UPDATE_BOARDING_PASS_SQL =
            "UPDATE boarding_passes SET is_boarded = ?, boarding_time = ?, boarding_group = ?, check_in_id = ? WHERE boarding_pass_id = ?";
    private static final String DELETE_BOARDING_PASS_SQL =
            "DELETE FROM boarding_passes WHERE boarding_pass_id = ?";
    private static final String FIND_BY_CHECK_IN_ID_SQL =
            "SELECT * FROM boarding_passes WHERE check_in_id = ?";

    @Override
    public void createBoardingPass(BoardingPass boardingPassObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(INSERT_BOARDING_PASS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setBoolean(1, boardingPassObj.isBoarded());
            statement.setTimestamp(2, boardingPassObj.getBoardingTime());
            statement.setString(3, boardingPassObj.getBoardingGroup());
            statement.setInt(4, boardingPassObj.getCheckInId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating boarding pass failed, no rows affected.");
            }

            SQLUtils.setGeneratedKey(statement, boardingPassObj::setBoardingPassId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BoardingPass getBoardingPassById(int boardingPassId) {
        BoardingPass boardingPass = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setInt(1, boardingPassId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    boardingPass = new BoardingPass();
                    boardingPass.setBoardingPassId(rs.getInt("boarding_pass_id"));
                    boardingPass.setBoarded(rs.getBoolean("is_boarded"));
                    boardingPass.setBoardingTime(rs.getTimestamp("boarding_time"));
                    boardingPass.setBoardingGroup(rs.getString("boarding_group"));
                    boardingPass.setCheckInId(rs.getInt("check_in_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardingPass;
    }

    @Override
    public BoardingPass getBoardingPassByCheckInId(int checkInId) {
        BoardingPass boardingPass = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_CHECK_IN_ID_SQL)
        ) {
            statement.setInt(1, checkInId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    boardingPass = extractBoardingPassFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting BoardingPass by CheckIn ID: ", e);
        }
        return boardingPass;
    }

    @Override
    public void updateBoardingPass(BoardingPass boardingPassObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(UPDATE_BOARDING_PASS_SQL)
        ) {
            statement.setBoolean(1, boardingPassObj.isBoarded());
            statement.setTimestamp(2, boardingPassObj.getBoardingTime());
            statement.setString(3, boardingPassObj.getBoardingGroup());
            statement.setInt(4, boardingPassObj.getCheckInId());
            statement.setInt(5, boardingPassObj.getBoardingPassId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBoardingPass(int boardingPassId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(DELETE_BOARDING_PASS_SQL)
        ) {
            statement.setInt(1, boardingPassId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BoardingPass extractBoardingPassFromResultSet(ResultSet rs) throws SQLException {
        BoardingPass boardingPass = new BoardingPass();
        boardingPass.setBoardingPassId(rs.getInt("boarding_pass_id"));
        boardingPass.setBoarded(rs.getBoolean("is_boarded"));
        boardingPass.setBoardingTime(rs.getTimestamp("boarding_time"));
        boardingPass.setBoardingGroup(rs.getString("boarding_group"));
        boardingPass.setCheckInId(rs.getInt("check_in_id"));
        return boardingPass;
    }
}
