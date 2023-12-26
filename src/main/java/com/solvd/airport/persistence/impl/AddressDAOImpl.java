package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Address;
import com.solvd.airport.persistence.mappers.AddressDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.*;

public class AddressDAOImpl implements AddressDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_ADDRESS_SQL = "INSERT INTO addresses " +
            "(street, city_subdivision, city, city_superdivision, country_code, postal_code, timezone) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ADDRESS_BY_ID_SQL = "SELECT * FROM addresses WHERE address_id = ?";

    private static final String UPDATE_ADDRESS_SQL = "UPDATE addresses SET " +
            "street = ?, city_subdivision = ?, city = ?, city_superdivision = ?, country_code = ?, " +
            "postal_code = ?, timezone = ? WHERE address_id = ?";

    private static final String DELETE_ADDRESS_SQL = "DELETE FROM addresses WHERE address_id = ?";

    @Override
    public void createAddress(Address address) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_ADDRESS_SQL)) {
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCitySubdivision());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getCitySuperdivision());
            statement.setString(5, address.getCountryCode());
            statement.setString(6, address.getPostalCode());
            statement.setString(7, address.getTimezone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address getAddressById(int id) {
        Address address = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ADDRESS_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                address = new Address();
                address.setAddressId(rs.getInt("address_id"));
                address.setStreet(rs.getString("street"));
                address.setCitySubdivision(rs.getString("city_subdivision"));
                address.setCity(rs.getString("city"));
                address.setCitySuperdivision(rs.getString("city_superdivision"));
                address.setCountry(rs.getString("country_code"));
                address.setPostalCode(rs.getString("postal_code"));
                address.setTimezone(rs.getString("timezone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM addresses WHERE street = ? AND city = ? AND postal_code = ? AND country_code = ?")) {
            statement.setString(1, street);
            statement.setString(2, city);
            statement.setString(3, postalCode);
            statement.setString(4, countryCode);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Address(
                        rs.getInt("address_id"),
                        rs.getString("street"),
                        rs.getString("city_subdivision"),
                        rs.getString("city"),
                        rs.getString("city_superdivision"),
                        rs.getString("country_code"),
                        rs.getString("postal_code"),
                        rs.getString("timezone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAddress(Address address) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_ADDRESS_SQL)) {
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCitySubdivision());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getCitySuperdivision());
            statement.setString(5, address.getCountryCode());
            statement.setString(6, address.getPostalCode());
            statement.setString(7, address.getTimezone());
            statement.setInt(8, address.getAddressId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(int id) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_ADDRESS_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
