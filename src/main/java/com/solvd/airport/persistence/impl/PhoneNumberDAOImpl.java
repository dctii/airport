package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.PhoneNumberDAO;
import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.util.SQLUtils;

import java.sql.*;

public class PhoneNumberDAOImpl implements PhoneNumberDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PHONE_SQL = "INSERT INTO phone_numbers (phone_number, extension) VALUES (?, ?)";
    private static final String SELECT_PHONE_BY_ID_SQL = "SELECT * FROM phone_numbers WHERE phone_number_id = ?";
    private static final String UPDATE_PHONE_SQL = "UPDATE phone_numbers SET phone_number = ?, extension = ? WHERE phone_number_id = ?";
    private static final String DELETE_PHONE_SQL = "DELETE FROM phone_numbers WHERE phone_number_id = ?";

    @Override
    public void createPhoneNumber(PhoneNumber phoneNumberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PHONE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            ps.setString(2, phoneNumberObj.getExtension());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, phoneNumberObj::setPhoneNumberId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(int phoneNumberId) {
        PhoneNumber phoneNumber = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PHONE_BY_ID_SQL)) {
            ps.setInt(1, phoneNumberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                phoneNumber = new PhoneNumber();
                phoneNumber.setPhoneNumberId(rs.getInt("phone_number_id"));
                phoneNumber.setPhoneNumber(rs.getString("phone_number"));
                phoneNumber.setExtension(rs.getString("extension"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

    @Override
    public PhoneNumber getPhoneNumberByNumber(String phoneNumber) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM phone_numbers WHERE phone_number = ?")) {
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PhoneNumber(
                        rs.getInt("phone_number_id"),
                        rs.getString("phone_number"),
                        rs.getString("extension")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updatePhoneNumber(PhoneNumber phoneNumberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PHONE_SQL)) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            ps.setString(2, phoneNumberObj.getExtension());
            ps.setInt(3, phoneNumberObj.getPhoneNumberId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePhoneNumber(int phoneNumberId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_PHONE_SQL)) {
            ps.setInt(1, phoneNumberId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
