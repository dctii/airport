package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PersonAddress;
import com.solvd.airport.persistence.mappers.PersonAddressDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class PersonAddressDAOImpl implements PersonAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PERSON_ADDRESS_SQL = "INSERT INTO person_addresses (person_info_id, address_id) VALUES (?, ?)";
    private static final String SELECT_PERSON_ADDRESS_SQL = "SELECT * FROM person_addresses WHERE person_info_id = ? AND address_id = ?";
    private static final String UPDATE_PERSON_ADDRESS_SQL = "UPDATE person_addresses SET address_id = ? WHERE person_info_id = ?";
    private static final String DELETE_PERSON_ADDRESS_SQL = "DELETE FROM person_addresses WHERE person_info_id = ? AND address_id = ?";

    @Override
    public void createPersonAddress(PersonAddress personAddress) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_PERSON_ADDRESS_SQL)) {
            statement.setInt(1, personAddress.getPersonInfoId());
            statement.setInt(2, personAddress.getAddressId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonAddress getPersonAddressById(int personInfoId, int addressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_PERSON_ADDRESS_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, addressId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new PersonAddress(rs.getInt("person_info_id"), rs.getInt("address_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePersonAddress(PersonAddress personAddress) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_PERSON_ADDRESS_SQL)) {
            statement.setInt(1, personAddress.getAddressId());
            statement.setInt(2, personAddress.getPersonInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonAddress(int personInfoId, int addressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_PERSON_ADDRESS_SQL)) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
