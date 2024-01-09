package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.persistence.TerminalDAO;
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

public class TerminalJDBCImpl implements TerminalDAO {

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createTerminal(Terminal terminalObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_TERMINAL_SQL)
        ) {
            ps.setString(1, terminalObj.getAirportCode());
            ps.setString(2, terminalObj.getTerminalCode());
            ps.setString(3, terminalObj.getTerminalName());
            ps.setBoolean(3, terminalObj.isInternational());
            ps.setBoolean(3, terminalObj.isDomestic());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesTerminalExist(String airportCode, String terminalCode) {
        boolean doesExist = false;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_TERMINAL_EXIST_SQL)
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

    private static final List<Field<?>> INSERT_TERMINAL_FIELDS = List.of(
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_TERMINAL_CODE),
            DSL.field(COL_TERMINAL_NAME),
            DSL.field(COL_IS_DOMESTIC),
            DSL.field(COL_IS_INTERNATIONAL)
    );

    private static final String INSERT_TERMINAL_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_TERMINAL_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_TERMINAL_FIELDS.size()))
            .getSQL();

    private static final String DOES_TERMINAL_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE)
                            )
            )
            .getSQL();
}
