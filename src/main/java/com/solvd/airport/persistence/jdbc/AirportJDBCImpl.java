package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.AirportDAO;
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

public class AirportJDBCImpl implements AirportDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRPORT_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
    INSERT INTO airports (airport_code, airport_name, address_id)
    VALUES (?, ?, ?)
 */
    private static final List<Field<?>> INSERT_AIRPORT_FIELDS = List.of(
            DSL.field(COL_AIRPORT_CODE),
            DSL.field(COL_AIRPORT_NAME),
            DSL.field(COL_ADDRESS_ID)
    );

    private static final String INSERT_AIRPORT_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRPORT_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRPORT_FIELDS.size()))
            .getSQL();

    @Override
    public int create(Airport airportObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_AIRPORT_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, airportObj.getAirportCode());
            SQLUtils.setStringOrNull(ps, 2, airportObj.getAirportName());
            SQLUtils.setIntOrNull(ps, 3, airportObj.getAddressId());

            SQLUtils.updateAndSetGeneratedId(ps, airportObj::setAirportId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
    SELECT * FROM airports
    WHERE airport_id = ?
 */
    private static final String SELECT_AIRPORT_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_ID))
            .getSQL();

    @Override
    public Airport getById(int airportId) {
        Airport airport = null;

        Connection conn = connectionPool.getConnection();
        try (
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
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return airport;
    }

    /*
    SELECT * FROM airports
    WHERE airport_code = ?
 */
    private static final String SELECT_AIRPORT_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    @Override
    public Airport getAirportByCode(String airportCode) {
        Airport airport = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_TERMINALS_BY_AIRPORT_CODE_SQL)
        ) {
            ps.setString(1, airportCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airport = extractAirportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return airport;
    }

    /*
    UPDATE airports
    SET airport_code = ?, airport_name = ?
    WHERE airport_id = ?
 */
    private static final String UPDATE_AIRPORT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRPORT_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRPORT_NAME), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_ID))
            .getSQL();

    @Override
    public void update(Airport airportObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRPORT_SQL)
        ) {
            ps.setString(1, airportObj.getAirportCode());
            SQLUtils.setStringOrNull(ps, 2, airportObj.getAirportName());
            SQLUtils.setIntOrNull(ps, 3, airportObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    DELETE FROM airports
    WHERE airport_id = ?
 */
    private static final String DELETE_AIRPORT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_ID))
            .getSQL();

    @Override
    public void delete(int airportId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRPORT_SQL)
        ) {
            ps.setInt(1, airportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    DELETE FROM airports
    WHERE airport_code = ?
 */
    private static final String DELETE_AIRPORT_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    @Override
    public void deleteAirportByCode(String airportCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRPORT_BY_CODE_SQL)
        ) {
            ps.setString(1, airportCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    SELECT COUNT(*) FROM airports
    WHERE airport_code = ?
 */
    private static final String DOES_AIRPORT_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    @Override
    public boolean doesAirportExist(String airportCode) {
        boolean doesExist = false;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_AIRPORT_EXIST_SQL)
        ) {
            ps.setString(1, airportCode);
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
        SELECT *
        FROM terminals
        WHERE airport_code = #{airportCode}
    */

    private static final String SELECT_TERMINALS_BY_AIRPORT_CODE_SQL = create
            .select()
            .from(DSL.table(TerminalDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRPORT_CODE))
            .getSQL();

    @Override
    public Set<Terminal> getTerminalsByAirportCode(String airportCode) {
        Set<Terminal> terminals = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_TERMINALS_BY_AIRPORT_CODE_SQL)
        ) {
            ps.setString(1, airportCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Terminal terminal = TerminalJDBCImpl.extractTerminalFromResultSet(rs);
                    terminals.add(terminal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return terminals;
    }

    private Airport extractAirportFromResultSet(ResultSet rs) {
        try {
            Airport airport = new Airport();
            airport.setAirportId(rs.getInt(COL_AIRPORT_ID));
            airport.setAirportCode(rs.getString(COL_AIRPORT_CODE));
            airport.setAirportName(rs.getString(COL_AIRPORT_NAME));
            airport.setAddressId(rs.getInt(COL_ADDRESS_ID));

            Set<Terminal> terminals = getTerminalsByAirportCode(airport.getAirportCode());
            airport.setTerminals(terminals);

            return airport;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Airport", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Airport" + e);
        }
    }


}
