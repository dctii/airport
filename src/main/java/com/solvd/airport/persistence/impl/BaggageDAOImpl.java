package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Baggage;
import com.solvd.airport.persistence.mappers.BaggageDAO;

import java.sql.*;

public class BaggageDAOImpl implements BaggageDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_BAGGAGE_SQL =
            "INSERT INTO baggage (baggage_code, weight, price, check_in_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_CODE_SQL =
            "SELECT * FROM baggage WHERE baggage_code = ?";
    private static final String UPDATE_BAGGAGE_SQL =
            "UPDATE baggage SET weight = ?, price = ?, check_in_id = ? WHERE baggage_code = ?";
    private static final String DELETE_BAGGAGE_SQL =
            "DELETE FROM baggage WHERE baggage_code = ?";
    private static final String UPDATE_BAGGAGE_WITH_BOARDING_PASS_ID_SQL =
            "UPDATE baggage SET boarding_pass_id = ? WHERE baggage_code = ?";

    @Override
    public void createBaggage(Baggage baggage) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_BAGGAGE_SQL)
        ) {
            statement.setString(1, baggage.getBaggageCode());
            statement.setBigDecimal(2, baggage.getWeight());
            statement.setBigDecimal(3, baggage.getPrice());
            statement.setInt(4, baggage.getCheckInId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Baggage getBaggageByCode(String code) {
        Baggage baggage = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(FIND_BY_CODE_SQL)
        ) {
            statement.setString(1, code);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    baggage = new Baggage();
                    baggage.setBaggageCode(rs.getString("baggage_code"));
                    baggage.setWeight(rs.getBigDecimal("weight"));
                    baggage.setPrice(rs.getBigDecimal("price"));
                    baggage.setCheckInId(rs.getInt("check_in_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baggage;
    }

    @Override
    public void updateBaggage(Baggage baggage) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_BAGGAGE_SQL)) {
            statement.setBigDecimal(1, baggage.getWeight());
            statement.setBigDecimal(2, baggage.getPrice());
            statement.setInt(3, baggage.getCheckInId());
            statement.setString(4, baggage.getBaggageCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBaggage(String code) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_BAGGAGE_SQL)) {
            statement.setString(1, code);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_BAGGAGE_WITH_BOARDING_PASS_ID_SQL)) {
            statement.setInt(1, boardingPassId);
            statement.setString(2, baggageCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
