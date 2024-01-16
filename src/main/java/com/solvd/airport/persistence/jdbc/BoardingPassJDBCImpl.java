package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class BoardingPassJDBCImpl implements BoardingPassDAO {
    private static final Logger LOGGER = LogManager.getLogger(BoardingPassJDBCImpl.class);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void create(BoardingPass boardingPassObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_BOARDING_PASS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setBoolean(1, boardingPassObj.isBoarded());
            ps.setTimestamp(2, boardingPassObj.getBoardingTime());
            ps.setString(3, boardingPassObj.getBoardingGroup());
            ps.setInt(4, boardingPassObj.getCheckInId());

            SQLUtils.updateAndSetGeneratedId(ps, boardingPassObj::setBoardingPassId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BoardingPass getById(int boardingPassId) {
        BoardingPass boardingPass = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, boardingPassId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boardingPass = extractBoardingPassFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardingPass;
    }

    @Override
    public BoardingPass getBoardingPassByCheckInId(int checkInId) {
        BoardingPass boardingPass = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CHECK_IN_ID_SQL)
        ) {
            ps.setInt(1, checkInId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boardingPass = extractBoardingPassFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting BoardingPass by CheckIn ID: ", e);
        }
        return boardingPass;
    }

    @Override
    public void update(BoardingPass boardingPassObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_BOARDING_PASS_SQL)
        ) {
            ps.setBoolean(1, boardingPassObj.isBoarded());
            ps.setTimestamp(2, boardingPassObj.getBoardingTime());
            ps.setString(3, boardingPassObj.getBoardingGroup());
            ps.setInt(4, boardingPassObj.getCheckInId());
            ps.setInt(5, boardingPassObj.getBoardingPassId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int boardingPassId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_BOARDING_PASS_SQL)
        ) {
            ps.setInt(1, boardingPassId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BoardingPass extractBoardingPassFromResultSet(ResultSet rs) throws SQLException {
        BoardingPass boardingPass = new BoardingPass();
        boardingPass.setBoardingPassId(rs.getInt(COL_BOARDING_PASS_ID));
        boardingPass.setBoarded(rs.getBoolean(COL_IS_BOARDED));
        boardingPass.setBoardingTime(rs.getTimestamp(COL_BOARDING_TIME));
        boardingPass.setBoardingGroup(rs.getString(COL_BOARDING_GROUP));
        boardingPass.setCheckInId(rs.getInt(COL_CHECK_IN_ID));

        return boardingPass;
    }

        /*
        "INSERT INTO boarding_passes (is_boarded, boarding_time, boarding_group, check_in_id) VALUES (?, ?, ?, ?)";
    */


    private static final List<Field<?>> INSERT_BOARDING_PASS_FIELDS = List.of(
            DSL.field(COL_IS_BOARDED),
            DSL.field(COL_BOARDING_TIME),
            DSL.field(COL_BOARDING_GROUP),
            DSL.field(COL_CHECK_IN_ID)
    );

    private static final String INSERT_BOARDING_PASS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_BOARDING_PASS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_BOARDING_PASS_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM boarding_passes WHERE boarding_pass_id = ?";
    */

    private static final String FIND_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOARDING_PASS_ID))
            .getSQL();

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

    /*
        "DELETE FROM boarding_passes WHERE boarding_pass_id = ?";
    */

    private static final String DELETE_BOARDING_PASS_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BOARDING_PASS_ID))
            .getSQL();

    /*
        "SELECT * FROM boarding_passes WHERE check_in_id = ?";
    */

    private static final String FIND_BY_CHECK_IN_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_CHECK_IN_ID))
            .getSQL();
}
