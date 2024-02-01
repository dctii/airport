package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PersonInfoDAO;
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

public class EmailAddressJDBCImpl implements EmailAddressDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.EMAIL_ADDRESS_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        "INSERT INTO email_addresses (email_address) VALUES (?)";
    */

    private static final String INSERT_EMAIL_SQL = create
            .insertInto(DSL.table(EmailAddressDAO.TABLE_NAME), DSL.field(EmailAddressDAO.COL_EMAIL_ADDRESS))
            .values(SQLConstants.PLACEHOLDER)
            .getSQL();


    @Override
    public int create(EmailAddress emailAddressObj) {
        Connection conn = connectionPool.getConnection();
        int newEmailAddressId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(INSERT_EMAIL_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, emailAddressObj.getEmailAddress());

            SQLUtils.updateAndSetGeneratedId(ps, emailAddressObj::setEmailAddressId);

            newEmailAddressId = emailAddressObj.getEmailAddressId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newEmailAddressId;
    }

        /*
        INSERT INTO person_email_addresses (person_info_id, address_id)
            VALUES (
            #{personAddressObj.personInfoId},
            #{personAddressObj.addressId}
            )
    */

    private static final List<Field<?>> INSERT_PERSON_ASSOCIATION_FIELDS = List.of(
            DSL.field(PersonInfoDAO.COL_PERSON_INFO_ID),
            DSL.field(COL_EMAIL_ADDRESS_ID)
    );
    private static final String INSERT_PERSON_ASSOCIATION_SQL = create
            .insertInto(DSL.table(PERSON_EMAIL_ADDRESSES_TABLE_NAME), INSERT_PERSON_ASSOCIATION_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_ASSOCIATION_FIELDS.size()))
            .getSQL();

    @Override
    public int createPersonAssociation(int personInfoId, int emailAddressId) {
        Connection conn = connectionPool.getConnection();

        int generatedId = 0;

        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PERSON_ASSOCIATION_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, emailAddressId);

            generatedId = SQLUtils.updateAndGetGeneratedKey(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return generatedId;
    }


    /*
        "SELECT * FROM email_addresses WHERE email_address_id = ?";
    */

    private static final String SELECT_EMAIL_BY_ID_SQL = create
            .selectFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    @Override
    public EmailAddress getById(int emailAddressId) {
        EmailAddress emailAddress = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_ID_SQL)
        ) {
            ps.setInt(1, emailAddressId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailAddress = extractEmailAddressFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return emailAddress;
    }

    /*
        "SELECT * FROM email_addresses WHERE email_address = ?";
     */

    private static final String SELECT_EMAIL_BY_EMAIL_ADDRESS_SQL = create
            .selectFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    @Override
    public EmailAddress getEmailAddressByEmail(String email) {
        EmailAddress emailAddress = null;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_EMAIL_ADDRESS_SQL)
        ) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailAddress = extractEmailAddressFromResultSet(rs);
                }
            } finally {
                connectionPool.releaseConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailAddress;
    }


    /*
        "UPDATE email_addresses SET email_address = ? WHERE email_address_id = ?";
     */

    private static final String UPDATE_EMAIL_SQL = create
            .update(DSL.table(EmailAddressDAO.TABLE_NAME))
            .set(DSL.field(EmailAddressDAO.COL_EMAIL_ADDRESS), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    @Override
    public void update(EmailAddress emailAddressObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_SQL)
        ) {
            ps.setString(1, emailAddressObj.getEmailAddress());
            ps.setInt(2, emailAddressObj.getEmailAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }


    /*
        "DELETE FROM email_addresses WHERE email_address_id = ?";
     */

    private static final String DELETE_EMAIL_SQL = create
            .deleteFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    @Override
    public void delete(int emailAddressId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_EMAIL_SQL)
        ) {
            ps.setInt(1, emailAddressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT pi.*
        FROM person_info pi
        JOIN person_email_addresses pea
        ON pi.person_info_id = pea.person_info_id
        WHERE pea.email_address_id = #{emailAddressId}
     */

    private static final String SELECT_PEOPLE_BY_EMAIL_ADDRESS_ID_SQL = create
            .select()
            .from(DSL.table(PersonInfoDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_EMAIL_ADDRESSES_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.PEA_EXPLICIT_COL_PERSON_INFO_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PEA_EXPLICIT_COL_EMAIL_ADDRESS_ID))
            .getSQL();

    @Override
    public Set<PersonInfo> getPeopleByEmailAddressId(int emailAddressId) {
        Set<PersonInfo> people = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PEOPLE_BY_EMAIL_ADDRESS_ID_SQL)
        ) {
            ps.setInt(1, emailAddressId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PersonInfo personInfo = new PersonInfo();
                    personInfo.setPersonInfoId(rs.getInt(PersonInfoDAO.COL_PERSON_INFO_ID));
                    personInfo.setSurname(rs.getString(PersonInfoDAO.COL_SURNAME));
                    personInfo.setGivenName(rs.getString(PersonInfoDAO.COL_GIVEN_NAME));
                    personInfo.setMiddleName(rs.getString(PersonInfoDAO.COL_MIDDLE_NAME));
                    personInfo.setBirthdate(rs.getDate(PersonInfoDAO.COL_BIRTHDATE));
                    personInfo.setSex(rs.getString(PersonInfoDAO.COL_SEX));

                    people.add(personInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return people;
    }

    private EmailAddress extractEmailAddressFromResultSet(ResultSet rs) {
        try {
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setEmailAddressId(rs.getInt(COL_EMAIL_ADDRESS_ID));
            emailAddress.setEmailAddress(rs.getString(COL_EMAIL_ADDRESS));

            Set<PersonInfo> people = getPeopleByEmailAddressId(emailAddress.getEmailAddressId());
            emailAddress.setPeopleWithEmailAddress(people);

            return emailAddress;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of EmailAddress", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of EmailAddress" + e);
        }
    }

}
