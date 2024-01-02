package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PersonPhoneNumber;
import com.solvd.airport.persistence.PersonPhoneNumberDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class PersonPhoneNumberDAOImpl implements PersonPhoneNumberDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PERSON_PHONE_SQL = "INSERT INTO person_phone_numbers (person_info_id, phone_number_id) VALUES (?, ?)";
    private static final String SELECT_PERSON_PHONE_SQL = "SELECT * FROM person_phone_numbers WHERE person_info_id = ? AND phone_number_id = ?";
    private static final String UPDATE_PERSON_PHONE_SQL = "UPDATE person_phone_numbers SET phone_number_id = ? WHERE person_info_id = ?";
    private static final String DELETE_PERSON_PHONE_SQL = "DELETE FROM person_phone_numbers WHERE person_info_id = ? AND phone_number_id = ?";

    @Override
    public void createPersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_PERSON_PHONE_SQL)) {
            statement.setInt(1, personPhoneNumberObj.getPersonInfoId());
            statement.setInt(2, personPhoneNumberObj.getPhoneNumberId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonPhoneNumber getPersonPhoneNumberById(int personInfoId, int phoneNumberId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_PERSON_PHONE_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, phoneNumberId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new PersonPhoneNumber(rs.getInt("person_info_id"), rs.getInt("phone_number_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_PERSON_PHONE_SQL)) {
            statement.setInt(1, personPhoneNumberObj.getPhoneNumberId());
            statement.setInt(2, personPhoneNumberObj.getPersonInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonPhoneNumber(int personInfoId, int phoneNumberId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_PERSON_PHONE_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, phoneNumberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
