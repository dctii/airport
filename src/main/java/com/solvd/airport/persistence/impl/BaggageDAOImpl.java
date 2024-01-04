package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Baggage;
import com.solvd.airport.persistence.BaggageDAO;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.util.SQLConstants;
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

public class BaggageDAOImpl implements BaggageDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createBaggage(Baggage baggageObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_BAGGAGE_SQL)
        ) {
            ps.setString(1, baggageObj.getBaggageCode());
            ps.setBigDecimal(2, baggageObj.getWeight());
            ps.setBigDecimal(3, baggageObj.getPrice());
            ps.setInt(4, baggageObj.getCheckInId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Baggage getBaggageByCode(String baggageCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_CODE_SQL)
        ) {
            ps.setString(1, baggageCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractBaggageFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateBaggage(Baggage baggageObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_BAGGAGE_SQL)
        ) {
            ps.setBigDecimal(1, baggageObj.getWeight());
            ps.setBigDecimal(2, baggageObj.getPrice());
            ps.setInt(3, baggageObj.getCheckInId());
            ps.setString(4, baggageObj.getBaggageCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBaggage(String baggageCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(DELETE_BAGGAGE_SQL)
        ) {
            statement.setString(1, baggageCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_BAGGAGE_WITH_BOARDING_PASS_ID_SQL)
        ) {
            ps.setInt(1, boardingPassId);
            ps.setString(2, baggageCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Baggage extractBaggageFromResultSet(ResultSet rs) throws SQLException {
        Baggage baggage = new Baggage();
        baggage.setBaggageCode(rs.getString(COL_BAGGAGE_CODE));
        baggage.setWeight(rs.getBigDecimal(COL_WEIGHT));
        baggage.setPrice(rs.getBigDecimal(COL_PRICE));
        baggage.setCheckInId(rs.getInt(COL_CHECK_IN_ID));

        return baggage;
    }


    /*
        "INSERT INTO baggage (baggage_code, weight, price, check_in_id) VALUES (?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_BAGGAGE_FIELDS = List.of(
            DSL.field(COL_BAGGAGE_CODE),
            DSL.field(COL_WEIGHT),
            DSL.field(COL_PRICE),
            DSL.field(COL_CHECK_IN_ID)
    );
    private static final String INSERT_BAGGAGE_SQL = create
            .insertInto(DSL.table(TABLE_NAME),
                    INSERT_BAGGAGE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_BAGGAGE_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM baggage WHERE baggage_code = ?";
    */

    private static final String FIND_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    /*
        "UPDATE baggage SET weight = ?, price = ?, check_in_id = ? WHERE baggage_code = ?";
    */

    private static final String UPDATE_BAGGAGE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_WEIGHT), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CHECK_IN_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    /*
        "DELETE FROM baggage WHERE baggage_code = ?";
    */

    private static final String DELETE_BAGGAGE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    /*
        "UPDATE baggage SET boarding_pass_id = ? WHERE baggage_code = ?";
    */

    private static final String UPDATE_BAGGAGE_WITH_BOARDING_PASS_ID_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(BoardingPassDAO.COL_BOARDING_PASS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();
}
