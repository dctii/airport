package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PassportDAO;
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

public class PersonInfoJDBCImpl implements PersonInfoDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PERSON_INFO_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

    /*
        "INSERT INTO person_info (surname, given_name, middle_name, birthdate, sex) VALUES (?, ?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_PERSON_INFO_FIELDS = List.of(
            DSL.field(COL_SURNAME),
            DSL.field(COL_GIVEN_NAME),
            DSL.field(COL_MIDDLE_NAME),
            DSL.field(COL_BIRTHDATE),
            DSL.field(COL_SEX)
    );

    private static final String INSERT_PERSON_INFO_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PERSON_INFO_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_INFO_FIELDS.size()))
            .getSQL();

    @Override
    public int create(PersonInfo personInfoObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PERSON_INFO_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, personInfoObj.getSurname());
            ps.setString(2, personInfoObj.getGivenName());
            SQLUtils.setStringOrNull(ps, 3, personInfoObj.getMiddleName());
            ps.setDate(4, personInfoObj.getBirthdate());
            ps.setString(5, personInfoObj.getSex());

            SQLUtils.updateAndSetGeneratedId(ps, personInfoObj::setPersonInfoId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
        "SELECT * FROM person_info WHERE person_info_id = ?";
     */

    private static final String SELECT_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public PersonInfo getById(int personInfoId) {
        PersonInfo personInfo = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personInfo = extractPersonInfoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return personInfo;
    }

    /*
        SELECT pi.person_info_id, pi.given_name, pi.middle_name, pi.birthdate, pi.sex
        FROM person_info pi
        JOIN passports p
        ON pi.person_info_id = p.person_info_id
        WHERE p.passport_number = #{passportNumber}
     */

    private static final String SELECT_PERSON_INFO_BY_PASSPORT_NUMBER_SQL = create
            .select(
                    DSL.field(EXPLICIT_COL_PERSON_INFO_ID),
                    DSL.field(EXPLICIT_COL_GIVEN_NAME),
                    DSL.field(EXPLICIT_COL_MIDDLE_NAME),
                    DSL.field(EXPLICIT_COL_SURNAME),
                    DSL.field(EXPLICIT_COL_BIRTHDATE),
                    DSL.field(EXPLICIT_COL_SEX)
            )
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(PassportDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            EXPLICIT_COL_PERSON_INFO_ID,
                            PassportDAO.EXPLICIT_COL_PERSON_INFO_ID)
            ).where(SQLUtils.eqPlaceholder(PassportDAO.EXPLICIT_COL_PASSPORT_NUMBER))
            .getSQL();


    @Override
    public PersonInfo getByPassportNumber(String passportNumber) {
        PersonInfo personInfo = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PERSON_INFO_BY_PASSPORT_NUMBER_SQL)
        ) {
            ps.setString(1, passportNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personInfo = extractPersonInfoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return personInfo;
    }

    /*
        "UPDATE person_info SET surname = ?, given_name = ?, middle_name = ?, birthdate = ?, sex = ? WHERE person_info_id = ?";
     */

    private static final String UPDATE_PERSON_INFO_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_SURNAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_GIVEN_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_MIDDLE_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_BIRTHDATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEX), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public void update(PersonInfo personInfoObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_INFO_SQL)
        ) {
            ps.setString(1, personInfoObj.getSurname());
            ps.setString(2, personInfoObj.getGivenName());
            SQLUtils.setStringOrNull(ps, 3, personInfoObj.getMiddleName());
            ps.setDate(4, personInfoObj.getBirthdate());
            ps.setString(5, personInfoObj.getSex());

            ps.setInt(6, personInfoObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "DELETE FROM person_info WHERE person_info_id = ?";
     */

    private static final String DELETE_PERSON_INFO_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public void delete(int personInfoId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_INFO_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT a.* FROM addresses a
        JOIN person_addresses pa
        ON pa.address_id = a.address_id
        WHERE pa.person_info_id = #{personInfoId}
     */

    private static final String SELECT_ADDRESSES_BY_PERSON_ID_SQL = create
            .select()
            .from(DSL.table(AddressDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_ADDRESSES_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.PA_EXPLICIT_COL_ADDRESS_ID,
                            AddressDAO.EXPLICIT_COL_ADDRESS_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PA_EXPLICIT_COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public Set<Address> getAddressesByPersonId(int personInfoId) {
        Set<Address> addresses = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_ADDRESSES_BY_PERSON_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Address address = extractAddressFromResultSet(rs);

                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return addresses;
    }

    /*
        SELECT pn.* FROM phone_numbers pn
        JOIN person_phone_numbers ppn
        ON ppn.phone_number_id = pn.phone_number_id
        WHERE ppn.person_info_id = #{personInfoId}
     */

    private static final String SELECT_PHONE_NUMBERS_BY_PERSON_ID_SQL = create
            .select()
            .from(DSL.table(AddressDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_PHONE_NUMBER_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.PPN_EXPLICIT_COL_PHONE_NUMBER_ID,
                            PhoneNumberDAO.EXPLICIT_COL_PHONE_NUMBER_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PPN_EXPLICIT_COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public Set<PhoneNumber> getPhoneNumbersByPersonId(int personInfoId) {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PHONE_NUMBERS_BY_PERSON_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhoneNumber phoneNumberObj = extractPhoneNumberFromResultSet(rs);

                    phoneNumbers.add(phoneNumberObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return phoneNumbers;
    }



    /*
        SELECT ea.* FROM email_addresses ea
        JOIN person_email_addresses pea
        ON pea.email_address_id = ea.email_address_id
        WHERE pea.person_info_id = #{personInfoId}
     */

    private static final String SELECT_EMAIL_ADDRESSES_BY_PERSON_ID_SQL = create
            .select()
            .from(DSL.table(AddressDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_EMAIL_ADDRESSES_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.PEA_EXPLICIT_COL_EMAIL_ADDRESS_ID,
                            EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PEA_EXPLICIT_COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public Set<EmailAddress> getEmailAddressesByPersonId(int personInfoId) {
        Set<EmailAddress> emailAddresses = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_ADDRESSES_BY_PERSON_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmailAddress emailAddress = extractEmailAddressFromResultSet(rs);

                    emailAddresses.add(emailAddress);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return emailAddresses;
    }

    public PersonInfo extractPersonInfoFromResultSet(ResultSet rs) {
        try {


            PersonInfo personInfo = new PersonInfo();
            personInfo.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));
            personInfo.setSurname(rs.getString(COL_SURNAME));
            personInfo.setGivenName(rs.getString(COL_GIVEN_NAME));
            personInfo.setMiddleName(rs.getString(COL_MIDDLE_NAME));
            personInfo.setBirthdate(rs.getDate(COL_BIRTHDATE));
            personInfo.setSex(rs.getString(COL_SEX));

            int personInfoId = personInfo.getPersonInfoId();

            Set<Address> addresses = getAddressesByPersonId(personInfoId);
            personInfo.setAddresses(addresses);

            Set<PhoneNumber> phoneNumbers = getPhoneNumbersByPersonId(personInfoId);
            personInfo.setPhoneNumbers(phoneNumbers);

            Set<EmailAddress> emailAddresses = getEmailAddressesByPersonId(personInfoId);
            personInfo.setEmailAddresses(emailAddresses);


            return personInfo;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of PersonInfo", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of PersonInfo" + e);
        }
    }

    private static Address extractAddressFromResultSet(ResultSet rs) {
        try {
            Address address = new Address();
            address.setAddressId(rs.getInt(AddressDAO.COL_ADDRESS_ID));
            address.setStreet(rs.getString(AddressDAO.COL_STREET));
            address.setCitySubdivision(rs.getString(AddressDAO.COL_CITY_SUBDIVISION));
            address.setCity(rs.getString(AddressDAO.COL_CITY));
            address.setCitySuperdivision(rs.getString(AddressDAO.COL_CITY_SUPERDIVISION));
            address.setCountryCode(rs.getString(AddressDAO.COL_COUNTRY_CODE));
            address.setPostalCode(rs.getString(AddressDAO.COL_POSTAL_CODE));
            address.setTimezone(rs.getString(AddressDAO.COL_TIMEZONE));
            return address;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Address", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Address" + e);
        }
    }

    private static PhoneNumber extractPhoneNumberFromResultSet(ResultSet rs) {
        try {
            PhoneNumber phoneNumberObj = new PhoneNumber();
            phoneNumberObj.setPhoneNumberId(rs.getInt(PhoneNumberDAO.COL_PHONE_NUMBER_ID));
            phoneNumberObj.setPhoneNumber(rs.getString(PhoneNumberDAO.COL_PHONE_NUMBER));
            phoneNumberObj.setExtension(rs.getString(PhoneNumberDAO.COL_EXTENSION));
            return phoneNumberObj;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of PhoneNumber", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of PhoneNumber" + e);
        }
    }

    private static EmailAddress extractEmailAddressFromResultSet(ResultSet rs) {
        try {
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setEmailAddressId(rs.getInt(EmailAddressDAO.COL_EMAIL_ADDRESS_ID));
            emailAddress.setEmailAddress(rs.getString(EmailAddressDAO.COL_EMAIL_ADDRESS));
            return emailAddress;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of EmailAddress", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of EmailAddress" + e);
        }
    }
}
