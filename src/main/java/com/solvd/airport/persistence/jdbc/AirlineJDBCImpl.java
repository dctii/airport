package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Airline;
import com.solvd.airport.persistence.AirlineDAO;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class AirlineJDBCImpl implements AirlineDAO {

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void create(Airline airlineObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            ps.setInt(3, airlineObj.getAddressId());

            SQLUtils.updateAndSetGeneratedId(ps, airlineObj::setAirlineId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAirlineWithoutAddress(Airline airlineObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_WITHOUT_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            SQLUtils.updateAndSetGeneratedId(ps, airlineObj::setAirlineId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO:
    @Override
    public Airline getById(int airlineId) {
        Airline airline = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRLINE_BY_ID_SQL)
        ) {
            ps.setInt(1, airlineId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airline = extractAirlineFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airline;
    }

    @Override
    public Airline getAirlineByCode(String airlineCode) {
        Airline airline = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRLINE_BY_CODE_SQL)
        ) {
            ps.setString(1, airlineCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airline = extractAirlineFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airline;
    }

    @Override
    public void update(Airline airlineObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_SQL)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            SQLUtils.setIntOrNull(ps, 3, airlineObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int airlineId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_SQL)
        ) {
            ps.setInt(1, airlineId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAirlineByCode(String airlineCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_BY_CODE_SQL)
        ) {
            ps.setString(1, airlineCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesAirlineExist(String airlineCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_AIRLINE_EXIST_SQL)
        ) {
            ps.setString(1, airlineCode);
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

    private static Airline extractAirlineFromResultSet(ResultSet rs) throws SQLException {
        Airline airline = new Airline();
        airline.setAirlineId(rs.getInt(COL_AIRLINE_ID));
        airline.setAirlineCode(rs.getString(COL_AIRLINE_CODE));
        airline.setAirlineName(rs.getString(COL_AIRLINE_NAME));
        airline.setAddressId(rs.getInt(COL_ADDRESS_ID));

        return airline;
    }

    private static final List<Field<?>> INSERT_AIRLINE_FIELDS = List.of(
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_AIRLINE_NAME),
            DSL.field(COL_ADDRESS_ID)
    );


    private static final String INSERT_AIRLINE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_FIELDS.size()))
            .getSQL();

    private static final List<Field<?>> INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS = List.of(
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_AIRLINE_NAME)
    );

    private static final String INSERT_AIRLINE_WITHOUT_ADDRESS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS.size()))
            .getSQL();

    private static final String SELECT_AIRLINE_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_ID))
            .getSQL();
    private static final String SELECT_AIRLINE_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    private static final String UPDATE_AIRLINE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRLINE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ADDRESS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    private static final String DELETE_AIRLINE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_ID))
            .getSQL();
    private static final String DELETE_AIRLINE_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    private static final String DOES_AIRLINE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();
}
