package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.persistence.dao.BaggageDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaggageDAOImpl implements BaggageDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_BAGGAGE_SQL = "INSERT INTO baggage (baggage_code, baggage_weight, baggage_price) VALUES (?, ?, ?)";
    private static final String UPDATE_BAGGAGE_SQL = "UPDATE baggage SET boarding_pass_id = ? WHERE baggage_code = ?";

    @Override
    public void insertBaggage(Baggage baggage) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_BAGGAGE_SQL)
        ) {
            statement.setString(1, baggage.getBaggageCode());
            statement.setBigDecimal(2, baggage.getBaggageWeight());
            statement.setBigDecimal(3, baggage.getBaggagePrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_BAGGAGE_SQL)) {
            statement.setInt(1, boardingPassId);
            statement.setString(2, baggageCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
