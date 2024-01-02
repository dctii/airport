package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.util.SQLUtils;

import java.sql.*;

public class EmailAddressDAOImpl implements EmailAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_EMAIL_SQL = "INSERT INTO email_addresses (email_address) VALUES (?)";
    private static final String SELECT_EMAIL_BY_ID_SQL = "SELECT * FROM email_addresses WHERE email_address_id = ?";
    private static final String UPDATE_EMAIL_SQL = "UPDATE email_addresses SET email_address = ? WHERE email_address_id = ?";
    private static final String DELETE_EMAIL_SQL = "DELETE FROM email_addresses WHERE email_address_id = ?";

    @Override
    public void createEmailAddress(EmailAddress emailAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_EMAIL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, emailAddressObj.getEmailAddress());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, emailAddressObj::setEmailAddressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EmailAddress getEmailAddressById(int emailAddressId) {
        EmailAddress emailAddress = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_ID_SQL)) {
            ps.setInt(1, emailAddressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailAddress = new EmailAddress();
                emailAddress.setEmailAddressId(rs.getInt("email_address_id"));
                emailAddress.setEmailAddress(rs.getString("email_address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailAddress;
    }

    @Override
    public EmailAddress getEmailAddressByEmail(String email) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM email_addresses WHERE email_address = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EmailAddress(
                        rs.getInt("email_address_id"),
                        rs.getString("email_address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateEmailAddress(EmailAddress emailAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_SQL)) {
            ps.setString(1, emailAddressObj.getEmailAddress());
            ps.setInt(2, emailAddressObj.getEmailAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmailAddress(int emailAddressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_EMAIL_SQL)) {
            ps.setInt(1, emailAddressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
