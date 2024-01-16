package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Terminal;
import com.solvd.airport.persistence.TerminalDAO;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class TerminalJDBCImpl implements TerminalDAO {

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void create(Terminal terminalObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_TERMINAL_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, terminalObj.getAirportCode());
            ps.setString(2, terminalObj.getTerminalCode());
            ps.setString(3, terminalObj.getTerminalName());
            ps.setBoolean(3, terminalObj.isInternational());
            ps.setBoolean(3, terminalObj.isDomestic());

            SQLUtils.updateAndSetGeneratedId(ps, terminalObj::setTerminalId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO
    @Override
    public Terminal getById(int terminalId) {
        Terminal terminal = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_TERMINAL_BY_ID_SQL)
        ) {
            ps.setInt(1, terminalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    terminal = extractTerminalFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terminal;
    }

    @Override
    public Terminal getTerminalByCodes(String airportCode, String terminalCode) {
        Terminal terminal = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_TERMINAL_BY_CODES_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    terminal = extractTerminalFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terminal;
    }

    // TODO
    @Override
    public void update(Terminal terminalObj) {

    }

    // TODO
    @Override
    public void delete(int terminalId) {

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

    private static Terminal extractTerminalFromResultSet(ResultSet rs) throws SQLException {
        Terminal terminal = new Terminal();
        terminal.setTerminalId(rs.getInt(COL_TERMINAL_ID));
        terminal.setAirportCode(rs.getString(COL_AIRPORT_CODE));
        terminal.setTerminalCode(rs.getString(COL_TERMINAL_CODE));
        terminal.setTerminalName(rs.getString(COL_TERMINAL_NAME));
        terminal.setInternational(rs.getBoolean(COL_IS_INTERNATIONAL));
        terminal.setDomestic(rs.getBoolean(COL_IS_DOMESTIC));

        return terminal;
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

    private static final String SELECT_TERMINAL_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TERMINAL_ID))
            .getSQL();
    private static final String SELECT_TERMINAL_BY_CODES_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
            )
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
