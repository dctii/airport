package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class GateJDBCImpl implements GateDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void create(Gate gateObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_GATE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, gateObj.getGateCode());
            ps.setString(2, gateObj.getAirportCode());
            ps.setString(3, gateObj.getTerminalCode());

            SQLUtils.updateAndSetGeneratedId(ps, gateObj::setGateId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Gate getById(int gateId) {
        Gate gate = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_GATE_BY_ID_SQL)
        ) {
            ps.setInt(1, gateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gate = extractGateFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gate;
    }

    public Gate getGateByCodes(String airportCode, String terminalCode, String gateCode) {
        Gate gate = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_GATE_BY_CODES_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
            ps.setString(3, gateCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gate = extractGateFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gate;
    }

    // TODO
    @Override
    public void update(Gate gateObj) {

    }


    // TODO
    @Override
    public void delete(int gateId) {

    }

    // TODO
    @Override
    public void deleteByCodes(String airportCode, String terminalCode, String gateCode) {

    }

    @Override
    public boolean doesGateExist(String airportCode, String terminalCode, String gateCode) {
        boolean doesExist = false;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_GATE_EXIST_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
            ps.setString(3, gateCode);
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

    private static Gate extractGateFromResultSet(ResultSet rs) throws SQLException {
        Gate gate = new Gate();
        gate.setGateId(rs.getInt(COL_GATE_ID));
        gate.setGateCode(rs.getString(COL_GATE_CODE));
        gate.setAirportCode(rs.getString(COL_AIRPORT_CODE));
        gate.setTerminalCode(rs.getString(COL_TERMINAL_CODE));

        return gate;
    }


    // SQL Statements

    private static final List<Field<?>> INSERT_GATE_FIELDS = List.of(
            DSL.field(COL_GATE_CODE),
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_TERMINAL_CODE)
    );

    private static final String INSERT_GATE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_GATE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_GATE_FIELDS.size()))
            .getSQL();

    private static final String SELECT_GATE_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_GATE_ID))
            .getSQL();
    private static final String SELECT_GATE_BY_CODES_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
                            .and(SQLUtils.eqPlaceholder(COL_GATE_CODE))
            )
            .getSQL();


    private static final String DOES_GATE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
                            .and(SQLUtils.eqPlaceholder(COL_GATE_CODE))
            )
            .getSQL();


}
