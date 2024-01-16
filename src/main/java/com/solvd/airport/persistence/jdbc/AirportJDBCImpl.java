package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.persistence.AirportDAO;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class AirportJDBCImpl implements AirportDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void create(Airport airportObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRPORT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, airportObj.getAirportCode());
            ps.setString(2, airportObj.getAirportName());
            ps.setInt(3, airportObj.getAddressId());

            SQLUtils.updateAndSetGeneratedId(ps, airportObj::setAirportId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAirportWithoutAddress(Airport airportObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRPORT_WITHOUT_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, airportObj.getAirportCode());
            ps.setString(2, airportObj.getAirportName());

            SQLUtils.updateAndSetGeneratedId(ps, airportObj::setAirportId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO:
    @Override
    public Airport getById(int airportId) {
        Airport airport = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRPORT_BY_ID_SQL)
        ) {
            ps.setInt(1, airportId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airport = extractAirportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airport;
    }

    @Override
    public Airport getAirportByCode(String airportCode) {
        Airport airport = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRPORT_BY_CODE_SQL)
        ) {
            ps.setString(1, airportCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airport = extractAirportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airport;
    }

    @Override
    public void update(Airport airportObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRPORT_SQL)
        ) {
            ps.setString(1, airportObj.getAirportName());
            ps.setString(2, airportObj.getAirportCode());
            SQLUtils.setIntOrNull(ps, 3, airportObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int airportId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRPORT_SQL)
        ) {
            ps.setInt(1, airportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAirportByCode(String airportCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRPORT_BY_CODE_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesAirportExist(String airportCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_AIRPORT_EXIST_SQL)
        ) {
            ps.setString(1, airportCode);
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

    private static Airport extractAirportFromResultSet(ResultSet rs) throws SQLException {
        Airport airport = new Airport();
        airport.setAirportId(rs.getInt(COL_AIRPORT_ID));
        airport.setAirportCode(rs.getString(COL_AIRPORT_CODE));
        airport.setAirportName(rs.getString(COL_AIRPORT_NAME));
        airport.setAddressId(rs.getInt(COL_ADDRESS_ID));
        return airport;
    }

    // SQL Statements

    private static final List<Field<?>> INSERT_AIRPORT_FIELDS = List.of(
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_AIRPORT_NAME),
            DSL.field(COL_ADDRESS_ID)
    );

    private static final String INSERT_AIRPORT_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRPORT_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRPORT_FIELDS.size()))
            .getSQL();

    private static final List<Field<?>> INSERT_AIRPORT_WITHOUT_ADDRESS_FIELDS = List.of(
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_AIRPORT_NAME)
    );

    private static final String INSERT_AIRPORT_WITHOUT_ADDRESS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRPORT_WITHOUT_ADDRESS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRPORT_WITHOUT_ADDRESS_FIELDS.size()))
            .getSQL();

    private static final String SELECT_AIRPORT_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_ID))
            .getSQL();
    private static final String SELECT_AIRPORT_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    private static final String UPDATE_AIRPORT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRPORT_NAME), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    private static final String DELETE_AIRPORT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_ID))
            .getSQL();
    private static final String DELETE_AIRPORT_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    private static final String DOES_AIRPORT_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();
}
