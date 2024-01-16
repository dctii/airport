package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.BoardingPassDAO;
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
import java.util.List;

public class BoardingPassJDBCImpl implements BoardingPassDAO {

    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOARDING_PASS_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    private static final List<Field<?>> INSERT_BOARDING_PASS_FIELDS = List.of(
            DSL.field(COL_IS_BOARDED),
            DSL.field(COL_BOARDING_TIME),
            DSL.field(COL_BOARDING_GROUP),
            DSL.field(COL_CHECK_IN_ID)
    );

    /*
        "INSERT INTO boarding_passes (is_boarded, boarding_time, boarding_group, check_in_id) VALUES (?, ?, ?, ?)";
    */
    private static final String INSERT_BOARDING_PASS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_BOARDING_PASS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_BOARDING_PASS_FIELDS.size()))
            .getSQL();

    @Override
    public int create(BoardingPass boardingPassObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(INSERT_BOARDING_PASS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            SQLUtils.setBooleanOrFalse(ps, 1, boardingPassObj.isBoarded());
            SQLUtils.setTimestampOrNull(ps, 2, boardingPassObj.getBoardingTime());
            SQLUtils.setStringOrNull(ps, 3, boardingPassObj.getBoardingGroup());
            SQLUtils.setIntOrNull(ps, 4, boardingPassObj.getCheckInId());

            SQLUtils.updateAndSetGeneratedId(ps, boardingPassObj::setBoardingPassId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
        "SELECT * FROM boarding_passes WHERE boarding_pass_id = ?";
    */

    private static final String SELECT_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOARDING_PASS_ID))
            .getSQL();

    @Override
    public BoardingPass getById(int boardingPassId) {
        BoardingPass boardingPass = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)
        ) {
            ps.setInt(1, boardingPassId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boardingPass = extractBoardingPassFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return boardingPass;
    }

    /*
        "SELECT * FROM boarding_passes WHERE check_in_id = ?";
    */

    private static final String SELECT_BOARDING_PASS_BY_CHECK_IN_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();

    @Override
    public BoardingPass getBoardingPassByCheckInId(int checkInId) {
        BoardingPass boardingPass = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BOARDING_PASS_BY_CHECK_IN_ID_SQL)
        ) {
            ps.setInt(1, checkInId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boardingPass = extractBoardingPassFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting BoardingPass by CheckIn ID: ", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return boardingPass;
    }

    /*
        "UPDATE boarding_passes SET is_boarded = ?, boarding_time = ?, boarding_group = ?, check_in_id = ? WHERE boarding_pass_id = ?";
    */

    private static final String UPDATE_BOARDING_PASS_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_IS_BOARDED), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_BOARDING_TIME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_BOARDING_GROUP), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CHECK_IN_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BOARDING_PASS_ID))
            .getSQL();

    @Override
    public void update(BoardingPass boardingPassObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_BOARDING_PASS_SQL)
        ) {
            SQLUtils.setBooleanOrFalse(ps, 1, boardingPassObj.isBoarded());
            SQLUtils.setTimestampOrNull(ps, 2, boardingPassObj.getBoardingTime());
            SQLUtils.setStringOrNull(ps, 3, boardingPassObj.getBoardingGroup());
            SQLUtils.setIntOrNull(ps, 4, boardingPassObj.getCheckInId());

            ps.setInt(5, boardingPassObj.getBoardingPassId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM boarding_passes WHERE boarding_pass_id = ?";
    */

    private static final String DELETE_BOARDING_PASS_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOARDING_PASS_ID))
            .getSQL();

    @Override
    public void delete(int boardingPassId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_BOARDING_PASS_SQL)
        ) {
            ps.setInt(1, boardingPassId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    private BoardingPass extractBoardingPassFromResultSet(ResultSet rs) {
        try {
            BoardingPass boardingPass = new BoardingPass();
            boardingPass.setBoardingPassId(rs.getInt(COL_BOARDING_PASS_ID));
            boardingPass.setBoarded(rs.getBoolean(COL_IS_BOARDED));
            boardingPass.setBoardingTime(rs.getTimestamp(COL_BOARDING_TIME));
            boardingPass.setBoardingGroup(rs.getString(COL_BOARDING_GROUP));
            boardingPass.setCheckInId(rs.getInt(COL_CHECK_IN_ID));

            return boardingPass;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of BoardingPass", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of BoardingPass" + e);
        }
    }

}
