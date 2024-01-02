package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PersonAddress;
import com.solvd.airport.persistence.PersonAddressDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class PersonAddressDAOImpl implements PersonAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PERSON_ADDRESS_SQL = "INSERT INTO person_addresses (person_info_id, address_id) VALUES (?, ?)";
    private static final String SELECT_PERSON_ADDRESS_SQL = "SELECT * FROM person_addresses WHERE person_info_id = ? AND address_id = ?";
    private static final String UPDATE_PERSON_ADDRESS_SQL = "UPDATE person_addresses SET address_id = ? WHERE person_info_id = ?";
    private static final String DELETE_PERSON_ADDRESS_SQL = "DELETE FROM person_addresses WHERE person_info_id = ? AND address_id = ?";

    @Override
    public void createPersonAddress(PersonAddress personAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_ADDRESS_SQL)) {
            ps.setInt(1, personAddressObj.getPersonInfoId());
            ps.setInt(2, personAddressObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonAddress getPersonAddressById(int personInfoId, int addressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_ADDRESS_SQL)) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, addressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PersonAddress(rs.getInt("person_info_id"), rs.getInt("address_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePersonAddress(PersonAddress personAddressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_ADDRESS_SQL)) {
            ps.setInt(1, personAddressObj.getAddressId());
            ps.setInt(2, personAddressObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonAddress(int personInfoId, int addressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_ADDRESS_SQL)) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, addressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
