package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Passport;
import com.solvd.airport.persistence.mappers.PassportDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportDAOImpl implements PassportDAO {
    private static final String INSERT_PASSPORT = "INSERT INTO passports (passport_number, issue_date, expiry_date, person_info_id) VALUES (?, ?, ?, ?)";
    private static final String GET_PASSPORT_BY_ID = "SELECT * FROM passports WHERE passport_number = ?";
    private static final String UPDATE_PASSPORT = "UPDATE passports SET issue_date = ?, expiry_date = ?, person_info_id = ? WHERE passport_number = ?";
    private static final String DELETE_PASSPORT = "DELETE FROM passports WHERE passport_number = ?";

    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    @Override
    public void createPassport(Passport passport) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PASSPORT)) {
            preparedStatement.setString(1, passport.getPassportNumber());
            preparedStatement.setDate(2, passport.getIssueDate());
            preparedStatement.setDate(3, passport.getExpiryDate());
            preparedStatement.setInt(4, passport.getPersonInfoId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating passport", e);
        }
    }

    @Override
    public Passport getPassportById(String passportNumber) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(GET_PASSPORT_BY_ID)) {
            preparedStatement.setString(1, passportNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Passport(
                            resultSet.getString("passport_number"),
                            resultSet.getDate("issue_date"),
                            resultSet.getDate("expiry_date"),
                            resultSet.getInt("person_info_id")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving passport with number " + passportNumber, e);
        }
        return null;
    }

    @Override
    public void updatePassport(Passport passport) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PASSPORT)) {
            preparedStatement.setDate(1, passport.getIssueDate());
            preparedStatement.setDate(2, passport.getExpiryDate());
            preparedStatement.setInt(3, passport.getPersonInfoId());
            preparedStatement.setString(4, passport.getPassportNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating passport with number " + passport.getPassportNumber(), e);
        }
    }

    @Override
    public void deletePassport(String passportNumber) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PASSPORT)) {
            preparedStatement.setString(1, passportNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting passport with number " + passportNumber, e);
        }
    }
}
