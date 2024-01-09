package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Timezone;
import com.solvd.airport.persistence.TimezoneDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TimezoneJDBCImpl implements TimezoneDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createTimezone(Timezone timezoneObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_TIMEZONE_SQL)
        ) {
            ps.setString(1, timezoneObj.getTimezone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Timezone getTimezoneByTz(String timezone) {
        Timezone timezoneObj = null;
        try (
                Connection conn = connectionPool.getConnection();
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
        }
        return timezoneObj;
    }

    @Override
    public void updateTimezoneByTz(String oldTimezone, Timezone newTimezone) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_TIMEZONE_BY_TZ_SQL)
        ) {
            ps.setString(1, newTimezone.getTimezone());
            ps.setString(2, oldTimezone);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTimezone(String timezone) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_TIMEZONE_SQL)
        ) {
            ps.setString(1, timezone);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesTimezoneExist(String timezone) {
        boolean doesExist = false;
        try (
                Connection conn = connectionPool.getConnection();
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
        }
        return doesExist;
    }

    private static Timezone extractTimezoneFromResultSet(ResultSet rs) throws SQLException {
        Timezone timezoneObj = new Timezone();
        timezoneObj.setTimezone(rs.getString(COL_TIMEZONE));
        return timezoneObj;
    }

    private static final String INSERT_TIMEZONE_SQL = create
            .insertInto(DSL.table(TABLE_NAME))
            .columns(DSL.field(COL_TIMEZONE))
            .values(SQLConstants.PLACEHOLDER)
            .getSQL();

    private static final String SELECT_TIMEZONE_BY_TZ_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    private static final String UPDATE_TIMEZONE_BY_TZ_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_TIMEZONE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    private static final String DELETE_TIMEZONE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();

    private static final String DOES_TIMEZONE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TIMEZONE))
            .getSQL();
}
