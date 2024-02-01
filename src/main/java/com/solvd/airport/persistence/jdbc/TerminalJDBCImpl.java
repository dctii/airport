package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.persistence.TerminalDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TerminalJDBCImpl implements TerminalDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.TERMINAL_JDBC_IMPL);

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        "INSERT INTO terminals (airport_code, terminal_code, terminal_name, is_domestic, is_international)
        VALUES (?, ?, ?)";
    */
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

    @Override
    public int create(Terminal terminalObj) {
        Connection conn = connectionPool.getConnection();
        int newTerminalId = 0;

        try (
                PreparedStatement ps = conn.prepareStatement(INSERT_TERMINAL_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, terminalObj.getAirportCode());
            ps.setString(2, terminalObj.getTerminalCode());
            SQLUtils.setStringOrNull(ps, 3, terminalObj.getTerminalName());
            SQLUtils.setBooleanOrFalse(ps, 4, terminalObj.isInternational());
            SQLUtils.setBooleanOrFalse(ps, 5, terminalObj.isDomestic());


            SQLUtils.updateAndSetGeneratedId(ps, terminalObj::setTerminalId);

            newTerminalId = terminalObj.getTerminalId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newTerminalId;
    }


    /*
        "SELECT * FROM terminals WHERE terminal_id = ?";
    */
    private static final String SELECT_TERMINAL_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TERMINAL_ID))
            .getSQL();

    @Override
    public Terminal getById(int terminalId) {
        Terminal terminal = null;

        Connection conn = connectionPool.getConnection();
        try (
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
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return terminal;
    }

    /*
        "SELECT * FROM terminals WHERE airport_code = ? AND terminal_code = ?";
    */
    private static final String SELECT_TERMINAL_BY_CODES_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
            )
            .getSQL();

    @Override
    public Terminal getTerminalByCodes(String airportCode, String terminalCode) {
        Terminal terminal = null;

        Connection conn = connectionPool.getConnection();
        try (
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
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return terminal;
    }

    /*
        "UPDATE terminals SET airport_code = ?, terminal_code = ?, terminal_name = ?, is_domestic = ?, is_international = ?
        WHERE terminal_id = ?";
    */
    private static final String UPDATE_TERMINAL_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRPORT_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TERMINAL_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TERMINAL_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_IS_DOMESTIC), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_IS_INTERNATIONAL), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_TERMINAL_ID))
            .getSQL();

    @Override
    public void update(Terminal terminalObj) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_TERMINAL_SQL)
        ) {
            ps.setString(1, terminalObj.getAirportCode());
            ps.setString(2, terminalObj.getTerminalCode());
            SQLUtils.setStringOrNull(ps, 3, terminalObj.getTerminalName());
            SQLUtils.setBooleanOrFalse(ps, 4, terminalObj.isInternational());
            SQLUtils.setBooleanOrFalse(ps, 5, terminalObj.isDomestic());

            ps.setInt(6, terminalObj.getTerminalId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    /*
    "DELETE FROM terminals WHERE terminal_id = ?";
*/
    private static final String DELETE_TERMINAL_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_TERMINAL_ID))
            .getSQL();


    @Override
    public void delete(int terminalId) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_TERMINAL_SQL)
        ) {
            ps.setInt(1, terminalId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "DELETE FROM terminals WHERE airport_code = ? AND terminal_code = ?";
*/
    private static final String DELETE_TERMINAL_BY_CODES_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE))
            )
            .getSQL();

    @Override
    public void deleteTerminalByCodes(String airportCode, String terminalCode) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_TERMINAL_BY_CODES_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

        /*
        "SELECT COUNT(*) FROM terminals WHERE airport_code = ? AND terminal_code = ?";
     */

    private static final String DOES_TERMINAL_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(COL_TERMINAL_CODE)
                            )
            )
            .getSQL();

    @Override
    public boolean doesTerminalExist(String airportCode, String terminalCode) {
        boolean doesExist = false;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_TERMINAL_EXIST_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
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

    /*
        SELECT g.*
        FROM gates g
        WHERE g.airport_code = #{airportCode}
        AND g.terminal_code = #{terminalCode}
     */

    private static final String SELECT_GATES_BY_CODES_SQL = create
            .select(DSL.field(GateDAO.ALL_COLUMNS))
            .from(DSL.table(GateDAO.TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(GateDAO.EXPLICIT_COL_AIRPORT_CODE)
                            .and(SQLUtils.eqPlaceholder(GateDAO.EXPLICIT_COL_TERMINAL_CODE))
            )
            .getSQL();

    @Override
    public Set<Gate> getGatesByCodes(String airportCode, String terminalCode) {
        Set<Gate> gates = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_GATES_BY_CODES_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.setString(2, terminalCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Gate gate = extractGateFromResultSet(rs);

                    gates.add(gate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return gates;
    }

    static Terminal extractTerminalFromResultSet(ResultSet rs) {
        try {
            Terminal terminal = new Terminal();
            terminal.setTerminalId(rs.getInt(COL_TERMINAL_ID));
            terminal.setAirportCode(rs.getString(COL_AIRPORT_CODE));
            terminal.setTerminalCode(rs.getString(COL_TERMINAL_CODE));
            terminal.setTerminalName(rs.getString(COL_TERMINAL_NAME));
            terminal.setInternational(rs.getBoolean(COL_IS_INTERNATIONAL));
            terminal.setDomestic(rs.getBoolean(COL_IS_DOMESTIC));

            return terminal;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Terminal", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Terminal" + e);
        }
    }

    private static Gate extractGateFromResultSet(ResultSet rs) {
        try {
            Gate gate = new Gate();
            gate.setGateId(rs.getInt(GateDAO.COL_GATE_ID));
            gate.setGateCode(rs.getString(GateDAO.COL_GATE_CODE));
            gate.setAirportCode(rs.getString(GateDAO.COL_AIRPORT_CODE));
            gate.setTerminalCode(rs.getString(GateDAO.COL_TERMINAL_CODE));
            return gate;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Gate", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Gate" + e);
        }
    }
}
