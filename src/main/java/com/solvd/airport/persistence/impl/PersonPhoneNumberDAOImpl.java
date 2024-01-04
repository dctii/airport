package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.PersonPhoneNumber;
import com.solvd.airport.persistence.PersonPhoneNumberDAO;
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

public class PersonPhoneNumberDAOImpl implements PersonPhoneNumberDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createPersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_PHONE_SQL)
        ) {
            ps.setInt(1, personPhoneNumberObj.getPersonInfoId());
            ps.setInt(2, personPhoneNumberObj.getPhoneNumberId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonPhoneNumber getPersonPhoneNumberById(int personInfoId, int phoneNumberId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_PHONE_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, phoneNumberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractPersonPhoneNumberFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(UPDATE_PERSON_PHONE_SQL)
        ) {
            statement.setInt(1, personPhoneNumberObj.getPhoneNumberId());
            statement.setInt(2, personPhoneNumberObj.getPersonInfoId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonPhoneNumber(int personInfoId, int phoneNumberId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(DELETE_PERSON_PHONE_SQL)
        ) {
            statement.setInt(1, personInfoId);
            statement.setInt(2, phoneNumberId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PersonPhoneNumber extractPersonPhoneNumberFromResultSet(ResultSet rs) throws SQLException {
        PersonPhoneNumber personPhoneNumber = new PersonPhoneNumber();
        personPhoneNumber.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));
        personPhoneNumber.setPhoneNumberId(rs.getInt(COL_PHONE_NUMBER_ID));

        return personPhoneNumber;
    }

        /*
        "INSERT INTO person_phone_numbers (person_info_id, phone_number_id) VALUES (?, ?)";
    */


    private static final List<Field<?>> INSERT_PERSON_PHONE_FIELDS = List.of(
            DSL.field(COL_PERSON_INFO_ID),
            DSL.field(COL_PHONE_NUMBER_ID)
    );

    private static final String INSERT_PERSON_PHONE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PERSON_PHONE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_PHONE_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM person_phone_numbers WHERE person_info_id = ? AND phone_number_id = ?";
     */


    private static final String SELECT_PERSON_PHONE_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            )
            .getSQL();

    /*
        "UPDATE person_phone_numbers SET phone_number_id = ? WHERE person_info_id = ?";
     */

    private static final String UPDATE_PERSON_PHONE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PHONE_NUMBER_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    /*
        "DELETE FROM person_phone_numbers WHERE person_info_id = ? AND phone_number_id = ?";
     */


    private static final String DELETE_PERSON_PHONE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            )
            .getSQL();
}
