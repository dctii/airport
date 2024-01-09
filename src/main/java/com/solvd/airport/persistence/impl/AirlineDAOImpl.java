package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Airline;
import com.solvd.airport.persistence.AirlineDAO;
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

public class AirlineDAOImpl implements AirlineDAO {

    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createAirline(Airline airlineObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_SQL)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            ps.setInt(3, airlineObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createAirlineWithoutAddress(Airline airlineObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_WITHOUT_ADDRESS_SQL)
        ) {
            ps.setString(1, airlineObj.getAirlineCode());
            ps.setString(2, airlineObj.getAirlineName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesAirlineExist(String airlineCode) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DOES_AIRLINE_EXIST_SQL)
        ) {
            ps.setString(1, airlineCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final List<Field<?>> INSERT_AIRLINE_FIELDS = List.of(
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_AIRLINE_NAME),
            DSL.field(COL_ADDRESS_ID)
    );


    private static final String INSERT_AIRLINE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_FIELDS.size()))
            .getSQL();

    private static final List<Field<?>> INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS = List.of(
            DSL.field(COL_AIRLINE_CODE),
            DSL.field(COL_AIRLINE_NAME)
    );

    private static final String INSERT_AIRLINE_WITHOUT_ADDRESS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_WITHOUT_ADDRESS_FIELDS.size()))
            .getSQL();

    private static final String DOES_AIRLINE_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_CODE))
            .getSQL();
}
