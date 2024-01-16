package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Timezone;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.TimezoneDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TimezoneJDBCImpl implements TimezoneDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.TIMEZONE_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        "INSERT INTO timezones (timezone) VALUES (?)";
    */
    private static final String INSERT_TIMEZONE_SQL = create
            .insertInto(DSL.table(TABLE_NAME))
            .columns(DSL.field(COL_TIMEZONE))
            .values(SQLConstants.PLACEHOLDER)
            .getSQL();

    @Override
    public int create(Timezone timezoneObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_TIMEZONE_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, timezoneObj.getTimezone());

            SQLUtils.updateAndSetGeneratedId(ps, timezoneObj::setTimezoneId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }


    /*
        "SELECT * FROM timezones WHERE timezone_id = ?";
    */
    private static final String SELECT_TIMEZONE_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE_ID))
            .getSQL();

    @Override
    public Timezone getById(int timezoneId) {
        Timezone timezoneObj = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_TIMEZONE_BY_ID_SQL)
        ) {
            ps.setInt(1, timezoneId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    timezoneObj = extractTimezoneFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return timezoneObj;
    }

    /*
        "SELECT * FROM timezones WHERE timezone = ?";
    */
    private static final String SELECT_TIMEZONE_BY_TZ_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    @Override
    public Timezone getTimezoneByTz(String timezone) {
        Timezone timezoneObj = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_TIMEZONE_BY_TZ_SQL)
        ) {
            ps.setString(1, timezone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    timezoneObj = extractTimezoneFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return timezoneObj;
    }

    /*
    "UPDATE timezones SET timezone = ?
            "WHERE timezone_id = ?";
*/
    private static final String UPDATE_TIMEZONE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_TIMEZONE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE_ID))
            .getSQL();

    @Override
    public void update(Timezone timezoneObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_TIMEZONE_SQL)
        ) {
            ps.setString(1, timezoneObj.getTimezone());

            ps.setInt(2, timezoneObj.getTimezoneId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "DELETE FROM timezones WHERE timezone_id = ?";
*/
    private static final String DELETE_TIMEZONE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE_ID))
            .getSQL();

    @Override
    public void delete(int timezoneId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_TIMEZONE_SQL)
        ) {
            ps.setInt(1, timezoneId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "DELETE FROM timezones WHERE timezone = ?";
*/
    private static final String DELETE_TIMEZONE_BY_TZ_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    @Override
    public void deleteTimezoneByTz(String timezone) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_TIMEZONE_BY_TZ_SQL)
        ) {
            ps.setString(1, timezone);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "SELECT COUNT(*) FROM timezones WHERE timezone = ?";
    */
    private static final String DOES_TIMEZONE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    @Override
    public boolean doesTimezoneExist(String timezone) {
        boolean doesExist = false;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_TIMEZONE_EXIST_SQL)
        ) {
            ps.setString(1, timezone);
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

    private static Timezone extractTimezoneFromResultSet(ResultSet rs) {
        try {
            Timezone timezoneObj = new Timezone();
            timezoneObj.setTimezoneId(rs.getInt(COL_TIMEZONE_ID));
            timezoneObj.setTimezone(rs.getString(COL_TIMEZONE));

            return timezoneObj;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Timezone", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Timezone" + e);
        }
    }
}
