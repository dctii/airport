package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.Airline;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.persistence.AirlineDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.DataAccessProvider;
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

public class AirlineJDBCImpl implements AirlineDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRLINE_JDBC_IMPL);

    private static final AddressDAO addressDAO = DataAccessProvider.getAddressDAO();

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

    /*
        INSERT INTO airlines (airline_code, airline_name, address_id)
        VALUES (
        #{airlineObj.airlineCode},
        #{airlineObj.airlineName},
        #{airlineObj.addressId}
        )
    */
    private static final List<Field<?>> INSERT_AIRLINE_FIELDS = List.of(
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_AIRLINE_NAME),
            DSL.field(COL_ADDRESS_ID)
    );


    private static final String INSERT_AIRLINE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_FIELDS.size()))
            .getSQL();

    @Override
    public int create(Airline airlineObj) {
        Connection conn = connectionPool.getConnection();

        int newAirlineId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_AIRLINE_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            SQLUtils.setIntOrNull(ps, 3, getAddressId(airlineObj));

            SQLUtils.updateAndSetGeneratedId(ps, airlineObj::setAirlineId);

            newAirlineId = airlineObj.getAirlineId();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newAirlineId;
    }



    /*
        SELECT * FROM airlines
        WHERE airline_id = #{airlineId}
    */
    private static final String SELECT_AIRLINE_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_ID))
            .getSQL();


    @Override
    public Airline getById(int airlineId) {
        Airline airline = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRLINE_BY_ID_SQL)
        ) {
            ps.setInt(1, airlineId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airline = extractAirlineFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return airline;
    }

    /*
        SELECT * FROM airlines
        WHERE airline_code = #{airlineCode}
    */
    private static final String SELECT_AIRLINE_BY_CODE_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    @Override
    public Airline getAirlineByCode(String airlineCode) {
        Airline airline = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_AIRLINE_BY_CODE_SQL)
        ) {
            ps.setString(1, airlineCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airline = extractAirlineFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return airline;
    }


    /*
        UPDATE airlines SET
        airline_code = #{airlineObj.airlineCode},
        airline_name = #{airlineObj.airlineName},
        address_id = #{airlineObj.addressId}
        WHERE airline_id = #{airlineObj.airlineId}
    */
    private static final String UPDATE_AIRLINE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRLINE_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_AIRLINE_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ADDRESS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_ID))
            .getSQL();

    @Override
    public void update(Airline airlineObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_SQL)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            SQLUtils.setIntOrNull(ps, 3, getAddressId(airlineObj));

            SQLUtils.setIntOrNull(ps, 4, airlineObj.getAirlineId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    /*
        UPDATE airlines SET
        airline_name = #{airlineObj.airlineName},
        address_id = #{airlineObj.addressId}
        WHERE airline_code = #{airlineObj.airlineCode}
     */
    private static final String UPDATE_AIRLINE_BY_CODE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_AIRLINE_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ADDRESS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    @Override
    public void updateAirlineByCode(Airline airlineObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_BY_CODE_SQL)
        ) {
            ps.setString(1, airlineObj.getAirlineName());
            SQLUtils.setIntOrNull(ps, 2, getAddressId(airlineObj));

            ps.setString(3, airlineObj.getAirlineCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        DELETE FROM airlines
        WHERE airline_id = #{airlineId}
     */
    private static final String DELETE_AIRLINE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_ID))
            .getSQL();

    @Override
    public void delete(int airlineId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_SQL)
        ) {
            ps.setInt(1, airlineId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        DELETE FROM airlines
        WHERE airline_code = #{airlineCode}
     */
    private static final String DELETE_AIRLINE_BY_CODE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    @Override
    public void deleteAirlineByCode(String airlineCode) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_BY_CODE_SQL)
        ) {
            ps.setString(1, airlineCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT COUNT(*) > 0 FROM airlines
        WHERE airline_code = #{airlineCode}
     */
    private static final String DOES_AIRLINE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();

    @Override
    public boolean doesAirlineExist(String airlineCode) {
        boolean doesExist = false;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_AIRLINE_EXIST_SQL)
        ) {
            ps.setString(1, airlineCode);
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

    private static Airline extractAirlineFromResultSet(ResultSet rs) {
        try {
        Airline airline = new Airline();
        airline.setAirlineId(rs.getInt(COL_AIRLINE_ID));
        airline.setAirlineCode(rs.getString(COL_AIRLINE_CODE));
        airline.setAirlineName(rs.getString(COL_AIRLINE_NAME));

        int addressId = rs.getInt(COL_ADDRESS_ID);
        if (rs.wasNull()) {
            airline.setAddress(null);
        } else {
            Address address = addressDAO.getById(addressId);
            airline.setAddress(address);
        }

        return airline;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Airline", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Airline" + e);
        }
    }

    private static Integer getAddressId(Airline airlineObj) {
        return airlineObj.getAddress() != null
                ? airlineObj.getAddress().getAddressId()
                : null;
    }

}
