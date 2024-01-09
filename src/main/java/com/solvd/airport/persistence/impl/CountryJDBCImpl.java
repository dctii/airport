package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Country;
import com.solvd.airport.persistence.CountryDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CountryDAOImpl implements CountryDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createCountry(Country countryObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_COUNTRY_SQL)
        ) {
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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_COUNTRY_BY_CODE_SQL)
        ) {
            ps.setString(1, countryCode);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    country = extractCountryFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }


    @Override
    public void updateCountry(Country countryObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_COUNTRY_SQL)
        ) {
            ps.setString(1, countryObj.getCountryName());
            ps.setString(2, countryObj.getCountryCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCountry(String countryCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_COUNTRY_SQL)
        ) {
            ps.setString(1, countryCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesCountryCodeExist(String countryCode) {
        boolean doesExist = false;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_COUNTRY_EXIST_SQL)
        ) {
            ps.setString(1, countryCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doesExist = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doesExist;
    }

    private static Country extractCountryFromResultSet(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setCountryCode(rs.getString(COL_COUNTRY_CODE));
        country.setCountryName(rs.getString(COL_COUNTRY_NAME));

        return country;
    }

        /*
            "INSERT INTO countries (country_code, country_name) VALUES (?, ?)";
    */

    private static final List<Field<?>> INSERT_COUNTRY_FIELDS = List.of(
            DSL.field(COL_COUNTRY_CODE),
            DSL.field(COL_COUNTRY_NAME)
    );
    private static final String INSERT_COUNTRY_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_COUNTRY_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_COUNTRY_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM countries WHERE country_code = ?";
     */

    private static final String SELECT_COUNTRY_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    /*
        "UPDATE countries SET country_name = ? WHERE country_code = ?";
     */

    private static final String UPDATE_COUNTRY_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_COUNTRY_NAME), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    /*
        "DELETE FROM countries WHERE country_code = ?";
     */

    private static final String DELETE_COUNTRY_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    /*
        "SELECT COUNT(*) FROM countries WHERE country_code = ?";
     */

    private static final String DOES_COUNTRY_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();
}
