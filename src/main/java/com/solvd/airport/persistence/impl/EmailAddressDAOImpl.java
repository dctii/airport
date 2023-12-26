package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.persistence.mappers.EmailAddressDAO;
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
    public void createEmailAddress(EmailAddress emailAddress) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_EMAIL_SQL)) {
            ps.setString(1, emailAddress.getEmailAddress());

            SQLUtils.setGeneratedKey(ps, emailAddress::setEmailAddressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EmailAddress getEmailAddressById(int id) {
        EmailAddress emailAddress = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_ID_SQL)) {
            ps.setInt(1, id);
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
    public void updateEmailAddress(EmailAddress emailAddress) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_SQL)) {
            ps.setString(1, emailAddress.getEmailAddress());
            ps.setInt(2, emailAddress.getEmailAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmailAddress(int id) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_EMAIL_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
