package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PersonEmailAddress;
import com.solvd.airport.persistence.PersonEmailAddressDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class PersonEmailAddressDAOImpl implements PersonEmailAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PERSON_EMAIL_SQL = "INSERT INTO person_email_addresses (person_info_id, email_address_id) VALUES (?, ?)";
    private static final String SELECT_PERSON_EMAIL_SQL = "SELECT * FROM person_email_addresses WHERE person_info_id = ? AND email_address_id = ?";
    private static final String UPDATE_PERSON_EMAIL_SQL = "UPDATE person_email_addresses SET email_address_id = ? WHERE person_info_id = ?";
    private static final String DELETE_PERSON_EMAIL_SQL = "DELETE FROM person_email_addresses WHERE person_info_id = ? AND email_address_id = ?";

    @Override
    public void createPersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_PERSON_EMAIL_SQL)) {
            statement.setInt(1, personEmailAddressObj.getPersonInfoId());
            statement.setInt(2, personEmailAddressObj.getEmailAddressId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonEmailAddress getPersonEmailAddressById(int personInfoId, int emailAddressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_PERSON_EMAIL_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, emailAddressId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new PersonEmailAddress(
                        rs.getInt("person_info_id"),
                        rs.getInt("email_address_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PersonEmailAddress getPersonEmailAddressByEmail(String emailAddress) {
        PersonEmailAddress personEmailAddress = null;
        final String SELECT_PERSON_EMAIL_BY_EMAIL_SQL =
                "SELECT pea.* " +
                        "FROM person_email_addresses AS pea " +
                        "JOIN email_addresses AS ea ON pea.email_address_id = ea.email_address_id " +
                        "WHERE ea.email_address = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_PERSON_EMAIL_BY_EMAIL_SQL)) {
            statement.setString(1, emailAddress);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                personEmailAddress = new PersonEmailAddress(
                        rs.getInt("person_info_id"),
                        rs.getInt("email_address_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personEmailAddress;
    }

    @Override
    public void updatePersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_PERSON_EMAIL_SQL)) {
            statement.setInt(1, personEmailAddressObj.getEmailAddressId());
            statement.setInt(2, personEmailAddressObj.getPersonInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonEmailAddress(int personInfoId, int emailAddressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_PERSON_EMAIL_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, emailAddressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
