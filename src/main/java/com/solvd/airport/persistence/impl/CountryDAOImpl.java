package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Country;
import com.solvd.airport.persistence.CountryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAOImpl implements CountryDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_COUNTRY_SQL = "INSERT INTO countries (country_code, country_name) VALUES (?, ?)";
    private static final String SELECT_COUNTRY_BY_CODE_SQL = "SELECT * FROM countries WHERE country_code = ?";
    private static final String UPDATE_COUNTRY_SQL = "UPDATE countries SET country_name = ? WHERE country_code = ?";
    private static final String DELETE_COUNTRY_SQL = "DELETE FROM countries WHERE country_code = ?";

    @Override
    public void createCountry(Country countryObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_COUNTRY_SQL)) {
            ps.setString(1, countryObj.getCountryCode());
            ps.setString(2, countryObj.getCountryName());
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO: Add exception messages
            e.printStackTrace();
        }
    }

    @Override
    public Country getCountryByCode(String countryCode) {
        Country country = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_COUNTRY_BY_CODE_SQL)) {
            ps.setString(1, countryCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                country = new Country();
                country.setCountryCode(rs.getString("country_code"));
                country.setCountryName(rs.getString("country_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    @Override
    public void updateCountry(Country countryObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_COUNTRY_SQL)) {
            ps.setString(1, countryObj.getCountryName());
            ps.setString(2, countryObj.getCountryCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCountry(String countryCode) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_COUNTRY_SQL)) {
            ps.setString(1, countryCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesCountryCodeExist(String countryCode) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM countries WHERE country_code = ?")) {
            ps.setString(1, countryCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
