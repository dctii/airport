package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.PersonEmailAddress;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PersonEmailAddressDAO;
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

public class PersonEmailAddressDAOImpl implements PersonEmailAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createPersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_EMAIL_SQL)
        ) {
            ps.setInt(1, personEmailAddressObj.getPersonInfoId());
            ps.setInt(2, personEmailAddressObj.getEmailAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonEmailAddress getPersonEmailAddressById(int personInfoId, int emailAddressId) {
        PersonEmailAddress personEmailAddress = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_EMAIL_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, emailAddressId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personEmailAddress = extractPersonEmailAddressFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personEmailAddress;
    }


    @Override
    public PersonEmailAddress getPersonEmailAddressByEmail(String emailAddress) {
        PersonEmailAddress personEmailAddress = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_EMAIL_BY_EMAIL_SQL)
        ) {
            ps.setString(1, emailAddress);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personEmailAddress = extractPersonEmailAddressFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personEmailAddress;
    }

    @Override
    public void updatePersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_EMAIL_SQL)
        ) {
            ps.setInt(1, personEmailAddressObj.getEmailAddressId());
            ps.setInt(2, personEmailAddressObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonEmailAddress(int personInfoId, int emailAddressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_EMAIL_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, emailAddressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PersonEmailAddress extractPersonEmailAddressFromResultSet(ResultSet rs) throws SQLException {
        PersonEmailAddress personEmailAddress = new PersonEmailAddress();
        personEmailAddress.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));
        personEmailAddress.setEmailAddressId(rs.getInt(COL_EMAIL_ADDRESS_ID));

        return personEmailAddress;
    }

        /*
        "INSERT INTO person_email_addresses (person_info_id, email_address_id) VALUES (?, ?)";
    */

    private static final List<Field<?>> INSERT_PERSON_EMAIL_FIELDS = List.of(
            DSL.field(COL_PERSON_INFO_ID),
            DSL.field(COL_EMAIL_ADDRESS_ID)
    );

    private static final String INSERT_PERSON_EMAIL_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PERSON_EMAIL_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_EMAIL_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM person_email_addresses WHERE person_info_id = ? AND email_address_id = ?";
     */

    private static final String SELECT_PERSON_EMAIL_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_EMAIL_ADDRESS_ID)))
            .getSQL();

    /*
        "SELECT pea.* " +
                    "FROM person_email_addresses AS pea " +
                    "JOIN email_addresses AS ea ON pea.email_address_id = ea.email_address_id " +
                    "WHERE ea.email_address = ?";
     */

    private static final String SELECT_PERSON_EMAIL_BY_EMAIL_SQL = create
            .select(DSL.field(ALL_COLUMNS))
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(EmailAddressDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            EXPLICIT_COL_EMAIL_ADDRESS_ID,
                            EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS_ID)
            )
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS))
            .getSQL();

    /*
        "UPDATE person_email_addresses SET email_address_id = ? WHERE person_info_id = ?";
     */

    private static final String UPDATE_PERSON_EMAIL_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_EMAIL_ADDRESS_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    /*
        "DELETE FROM person_email_addresses WHERE person_info_id = ? AND email_address_id = ?";
    */
    private static final String DELETE_PERSON_EMAIL_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID)
                            .and(SQLUtils.eqPlaceholder(COL_EMAIL_ADDRESS_ID))
            )
            .getSQL();
}
