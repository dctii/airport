package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.BaggageDAO;
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

public class BaggageJDBCImpl implements BaggageDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BAGGAGE_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

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

    @Override
    public int create(Baggage baggageObj) {
        Connection conn = connectionPool.getConnection();

        int newBaggageId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_BAGGAGE_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, baggageObj.getBaggageCode());
            SQLUtils.setBigDecimalOrNull(ps, 2, baggageObj.getWeight());
            SQLUtils.setBigDecimalOrNull(ps, 3, baggageObj.getPrice());
            SQLUtils.setIntOrNull(ps, 4, baggageObj.getCheckInId());

            SQLUtils.updateAndSetGeneratedId(ps, baggageObj::setBaggageId);

            newBaggageId = baggageObj.getBaggageId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newBaggageId;
    }

            /*
        "SELECT * FROM baggage WHERE baggage_id = ?";
    */

    private static final String SELECT_BAGGAGE_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_ID))
            .getSQL();

    @Override
    public Baggage getById(int baggageId) {
        Baggage baggage = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BAGGAGE_BY_ID_SQL)
        ) {
            ps.setInt(1, baggageId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    baggage = extractBaggageFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return baggage;
    }

        /*
        "SELECT * FROM baggage WHERE baggage_code = ?";
    */

    private static final String SELECT_BAGGAGE_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    @Override
    public Baggage getBaggageByCode(String baggageCode) {
        Baggage baggage = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BAGGAGE_BY_CODE_SQL)
        ) {
            ps.setString(1, baggageCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    baggage = extractBaggageFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return baggage;
    }

    /*
        "UPDATE baggage SET baggage_code = ? weight = ?, price = ?, check_in_id = ? WHERE baggage_id = ?";
    */

    private static final String UPDATE_BAGGAGE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_BAGGAGE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_WEIGHT), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CHECK_IN_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_ID))
            .getSQL();


    @Override
    public void update(Baggage baggageObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_BAGGAGE_SQL)
        ) {
            ps.setString(1, baggageObj.getBaggageCode());
            SQLUtils.setBigDecimalOrNull(ps, 2, baggageObj.getWeight());
            SQLUtils.setBigDecimalOrNull(ps, 3, baggageObj.getPrice());
            SQLUtils.setIntOrNull(ps, 4, baggageObj.getCheckInId());

            ps.setInt(5, baggageObj.getBaggageId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "UPDATE baggage SET weight = ?, price = ?, check_in_id = ? WHERE baggage_code = ?";
    */

    private static final String UPDATE_BAGGAGE_BY_CODE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_WEIGHT), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PRICE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CHECK_IN_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    @Override
    public void updateBaggageByCode(Baggage baggageObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_BAGGAGE_BY_CODE_SQL)
        ) {
            SQLUtils.setBigDecimalOrNull(ps, 1, baggageObj.getWeight());
            SQLUtils.setBigDecimalOrNull(ps, 2, baggageObj.getPrice());
            SQLUtils.setIntOrNull(ps, 3, baggageObj.getCheckInId());

            ps.setString(4, baggageObj.getBaggageCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    /*
        "DELETE FROM baggage WHERE baggage_id = ?";
    */

    private static final String DELETE_BAGGAGE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_ID))
            .getSQL();

    @Override
    public void delete(int baggageId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_BAGGAGE_SQL)
        ) {
            ps.setInt(1, baggageId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    /*
    "DELETE FROM baggage WHERE baggage_code = ?";
*/
    private static final String DELETE_BAGGAGE_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_BAGGAGE_CODE))
            .getSQL();

    @Override
    public void deleteBaggageByCode(String baggageCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_BAGGAGE_BY_CODE_SQL)
        ) {
            ps.setString(1, baggageCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    private static Baggage extractBaggageFromResultSet(ResultSet rs) {
        try {
            Baggage baggage = new Baggage();
            baggage.setBaggageId(rs.getInt(COL_BAGGAGE_ID));
            baggage.setBaggageCode(rs.getString(COL_BAGGAGE_CODE));
            baggage.setWeight(rs.getBigDecimal(COL_WEIGHT));
            baggage.setPrice(rs.getBigDecimal(COL_PRICE));
            baggage.setCheckInId(rs.getInt(COL_CHECK_IN_ID));

            return baggage;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Baggage", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Baggage" + e);
        }
    }


}
