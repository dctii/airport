package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.persistence.PhoneNumberDAO;
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

public class PhoneNumberJDBCImpl implements PhoneNumberDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PHONE_NUMBER_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        "INSERT INTO phone_numbers (phone_number, extension) VALUES (?, ?)";
    */

    private static final List<Field<?>> INSERT_PHONE_FIELDS = List.of(
            DSL.field(COL_PHONE_NUMBER),
            DSL.field(COL_EXTENSION)
    );

    private static final String INSERT_PHONE_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PHONE_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PHONE_FIELDS.size()))
            .getSQL();

    @Override
    public int create(PhoneNumber phoneNumberObj) {
        Connection conn = connectionPool.getConnection();

        int newPhoneNumberId = 0;
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PHONE_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            SQLUtils.setStringOrNull(ps, 2, phoneNumberObj.getExtension());

            SQLUtils.updateAndSetGeneratedId(ps, phoneNumberObj::setPhoneNumberId);

            newPhoneNumberId = phoneNumberObj.getPhoneNumberId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return newPhoneNumberId;
    }

    private static final List<Field<?>> INSERT_PERSON_ASSOCIATION_FIELDS = List.of(
            DSL.field(PersonInfoDAO.COL_PERSON_INFO_ID),
            DSL.field(COL_PHONE_NUMBER_ID)
    );
    private static final String INSERT_PERSON_ASSOCIATION_SQL = create
            .insertInto(DSL.table(PERSON_PHONE_NUMBER_TABLE_NAME), INSERT_PERSON_ASSOCIATION_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_ASSOCIATION_FIELDS.size()))
            .getSQL();

    @Override
    public int createPersonAssociation(int personInfoId, int phoneNumberId) {
        Connection conn = connectionPool.getConnection();
        int generatedId = 0;

        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PERSON_ASSOCIATION_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, phoneNumberId);

            generatedId = SQLUtils.updateAndGetGeneratedKey(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return generatedId;
    }


    /*
        "SELECT * FROM phone_numbers WHERE phone_number_id = ?";
     */

    private static final String SELECT_PHONE_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();


    @Override
    public PhoneNumber getById(int phoneNumberId) {
        Connection conn = connectionPool.getConnection();
        PhoneNumber phoneNumberObj = null;
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PHONE_BY_ID_SQL)
        ) {

            ps.setInt(1, phoneNumberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                phoneNumberObj = extractPhoneNumberFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return phoneNumberObj;
    }

    /*
        "SELECT * FROM phone_numbers WHERE phone_number = ?";
     */

    private static final String SELECT_PHONE_BY_PHONE_NUMBER_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER))
            .getSQL();

    @Override
    public PhoneNumber getPhoneNumberByNumber(String phoneNumber) {
        PhoneNumber phoneNumberObj = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PHONE_BY_PHONE_NUMBER_SQL)
        ) {

            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    phoneNumberObj = extractPhoneNumberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return phoneNumberObj;
    }

        /*
        "UPDATE phone_numbers SET phone_number = ?, extension = ? WHERE phone_number_id = ?";
     */

    private static final String UPDATE_PHONE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PHONE_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_EXTENSION), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();

    @Override
    public void update(PhoneNumber phoneNumberObj) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_PHONE_SQL)
        ) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            SQLUtils.setStringOrNull(ps, 2, phoneNumberObj.getExtension());

            ps.setInt(3, phoneNumberObj.getPhoneNumberId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM phone_numbers WHERE phone_number_id = ?";
    */
    private static final String DELETE_PHONE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();

    @Override
    public void delete(int phoneNumberId) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_PHONE_SQL)
        ) {
            ps.setInt(1, phoneNumberId);
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
        JOIN person_phone_numbers ppn
        ON pi.person_info_id = ppn.person_info_id
        WHERE ppn.phone_number_id = #{phoneNumberId}
     */

    private static final String SELECT_PEOPLE_BY_PHONE_NUMBER_ID_SQL = create
            .select()
            .from(DSL.table(PersonInfoDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_PHONE_NUMBER_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.PPN_EXPLICIT_COL_PERSON_INFO_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PPN_EXPLICIT_COL_PHONE_NUMBER_ID))
            .getSQL();

    @Override
    public Set<PersonInfo> getPeopleByPhoneNumberId(int phoneNumberId) {
        Set<PersonInfo> people = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PEOPLE_BY_PHONE_NUMBER_ID_SQL)
        ) {
            ps.setInt(1, phoneNumberId);
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

    private PhoneNumber extractPhoneNumberFromResultSet(ResultSet rs) {
        try {
            PhoneNumber phoneNumberObj = new PhoneNumber();
            phoneNumberObj.setPhoneNumberId(rs.getInt(COL_PHONE_NUMBER_ID));
            phoneNumberObj.setPhoneNumber(rs.getString(COL_PHONE_NUMBER));
            phoneNumberObj.setExtension(rs.getString(COL_EXTENSION));

            Set<PersonInfo> people = getPeopleByPhoneNumberId(phoneNumberObj.getPhoneNumberId());
            phoneNumberObj.setPeopleWithPhoneNumber(people);

            return phoneNumberObj;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of PhoneNumber", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of PhoneNumber" + e);
        }
    }


}
