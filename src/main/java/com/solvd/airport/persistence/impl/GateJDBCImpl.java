package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.persistence.GateDAO;
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

public class GateJDBCImpl implements GateDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createGate(Gate gateObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_GATE_SQL)
        ) {
            ps.setString(1, gateObj.getGateCode());
            ps.setString(2, gateObj.getAirportCode());
            ps.setString(3, gateObj.getTerminalCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesGateExist(String airportCode, String terminalCode, String gateCode) {
        boolean doesExist = false;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_GATE_EXIST_SQL)
        ) {
            ps.setString(1, airportCode);
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
