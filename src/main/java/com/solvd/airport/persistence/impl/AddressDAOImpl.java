package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Address;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.util.SQLUtils;

import java.sql.*;

public class AddressDAOImpl implements AddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_ADDRESS_SQL = "INSERT INTO addresses " +
            "(street, city_subdivision, city, city_superdivision, country_code, postal_code, timezone) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ADDRESS_BY_ID_SQL = "SELECT * FROM addresses WHERE address_id = ?";

    private static final String UPDATE_ADDRESS_SQL = "UPDATE addresses SET " +
            "street = ?, city_subdivision = ?, city = ?, city_superdivision = ?, country_code = ?, " +
            "postal_code = ?, timezone = ? WHERE address_id = ?";

    private static final String DELETE_ADDRESS_SQL = "DELETE FROM addresses WHERE address_id = ?";

    @Override
    public void createAddress(Address addressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, addressObj.getStreet());
            ps.setString(2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            ps.setString(4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            ps.setString(6, addressObj.getPostalCode());
            ps.setString(7, addressObj.getTimezone());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, addressObj::setAddressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        Address address = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ADDRESS_BY_ID_SQL)) {
            ps.setInt(1, addressId);
            ResultSet rs = ps.executeQuery();
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM addresses WHERE street = ? AND city = ? AND postal_code = ? AND country_code = ?")) {
            ps.setString(1, street);
            ps.setString(2, city);
            ps.setString(3, postalCode);
            ps.setString(4, countryCode);
            ResultSet rs = ps.executeQuery();
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
    public void updateAddress(Address addressObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ADDRESS_SQL)) {
            ps.setString(1, addressObj.getStreet());
            ps.setString(2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            ps.setString(4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            ps.setString(6, addressObj.getPostalCode());
            ps.setString(7, addressObj.getTimezone());
            ps.setInt(8, addressObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_ADDRESS_SQL)) {
            statement.setInt(1, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
