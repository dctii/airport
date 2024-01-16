package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Country;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.CountryDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CountryJDBCImpl implements CountryDAO {

    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.COUNTRY_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

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

    @Override
    public int create(Country countryObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_COUNTRY_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, countryObj.getCountryCode());
            ps.setString(2, countryObj.getCountryName());

            SQLUtils.updateAndSetGeneratedId(ps, countryObj::setCountryId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
    "SELECT * FROM countries WHERE country_id = ?";
*/
    private static final String SELECT_COUNTRY_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_ID))
            .getSQL();

    @Override
    public Country getById(int countryId) {
        Country country = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_COUNTRY_BY_ID_SQL)
        ) {
            ps.setInt(1, countryId);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    country = extractCountryFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return country;
    }

        /*
        "SELECT * FROM countries WHERE country_code = ?";
     */

    private static final String SELECT_COUNTRY_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    @Override
    public Country getCountryByCode(String countryCode) {
        Country country = null;
        Connection conn = connectionPool.getConnection();
        try (
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
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return country;
    }

    /*
        "UPDATE countries
        SET
            country_code = ?,
            country_name = ?
        WHERE country_id = ?";
     */


    private static final String UPDATE_COUNTRY_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_COUNTRY_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_COUNTRY_NAME), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_ID))
            .getSQL();

    @Override
    public void update(Country countryObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_COUNTRY_SQL)
        ) {
            ps.setString(1, countryObj.getCountryCode());
            ps.setString(2, countryObj.getCountryName());

            ps.setInt(3, countryObj.getCountryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


        /*
        "UPDATE countries
        SET
            country_code = ?,
            country_name = ?
        WHERE country_id = ?";
     */


    private static final String UPDATE_COUNTRY_BY_CODE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_COUNTRY_NAME), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    @Override
    public void updateCountryByCode(Country countryObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_COUNTRY_BY_CODE_SQL)
        ) {
            ps.setString(1, countryObj.getCountryName());

            ps.setString(2, countryObj.getCountryCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM countries WHERE country_id = ?";
     */

    private static final String DELETE_COUNTRY_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_ID))
            .getSQL();

    @Override
    public void delete(int countryId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_COUNTRY_SQL)
        ) {
            ps.setInt(1, countryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM countries WHERE country_code = ?";
     */

    private static final String DELETE_COUNTRY_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    @Override
    public void deleteCountryByCode(String countryCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_COUNTRY_BY_CODE_SQL)
        ) {
            ps.setString(1, countryCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "SELECT COUNT(*) FROM countries WHERE country_code = ?";
     */

    private static final String DOES_COUNTRY_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    @Override
    public boolean doesCountryCodeExist(String countryCode) {
        boolean doesExist = false;
        Connection conn = connectionPool.getConnection();
        try (
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
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return doesExist;
    }

    private static Country extractCountryFromResultSet(ResultSet rs) {
        try {
            Country country = new Country();
            country.setCountryId(rs.getInt(COL_COUNTRY_ID));
            country.setCountryCode(rs.getString(COL_COUNTRY_CODE));
            country.setCountryName(rs.getString(COL_COUNTRY_NAME));

            return country;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Country", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Country" + e);
        }
    }

}
