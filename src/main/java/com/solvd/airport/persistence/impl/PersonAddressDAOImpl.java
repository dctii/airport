package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.PersonAddress;
import com.solvd.airport.persistence.PersonAddressDAO;
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

public class PersonAddressDAOImpl implements PersonAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createPersonAddress(PersonAddress personAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_ADDRESS_SQL)
        ) {
            ps.setInt(1, personAddressObj.getPersonInfoId());
            ps.setInt(2, personAddressObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonAddress getPersonAddressById(int personInfoId, int addressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_ADDRESS_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, addressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractPersonAddressFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePersonAddress(PersonAddress personAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_ADDRESS_SQL)
        ) {
            ps.setInt(1, personAddressObj.getAddressId());
            ps.setInt(2, personAddressObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonAddress(int personInfoId, int addressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_ADDRESS_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, addressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PersonAddress extractPersonAddressFromResultSet(ResultSet rs) throws SQLException {
        PersonAddress personAddress = new PersonAddress();
        personAddress.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));
        personAddress.setAddressId(rs.getInt(COL_ADDRESS_ID));

        return personAddress;
    }

        /*
        "INSERT INTO person_addresses (person_info_id, address_id) VALUES (?, ?)";
    */

    private static final List<Field<?>> INSERT_PERSON_ADDRESS_FIELDS = List.of(
            DSL.field(COL_PERSON_INFO_ID),
            DSL.field(COL_ADDRESS_ID)
    );

    private static final String INSERT_PERSON_ADDRESS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PERSON_ADDRESS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_ADDRESS_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM person_addresses WHERE person_info_id = ? AND address_id = ?";
    */

    private static final String SELECT_PERSON_ADDRESS_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_ADDRESS_ID)
                            )
            )
            .getSQL();

    /*
        "UPDATE person_addresses SET address_id = ? WHERE person_info_id = ?";
    */

    private static final String UPDATE_PERSON_ADDRESS_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_ADDRESS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    /*
        "DELETE FROM person_addresses WHERE person_info_id = ? AND address_id = ?";
    */

    private static final String DELETE_PERSON_ADDRESS_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_ADDRESS_ID))
            )
            .getSQL();
}
