package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.AddressDAO;
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


public class AddressJDBCImpl implements AddressDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.ADDRESS_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        INSERT INTO addresses (street, city_subdivision, city, city_superdivision, country_code,
        postal_code, timezone)
        VALUES (
        #{addressObj.street},
        #{addressObj.citySubdivision},
        #{addressObj.city},
        #{addressObj.citySuperdivision},
        #{addressObj.countryCode},
        #{addressObj.postalCode},
        #{addressObj.timezone}
        )
*/
    private static final List<Field<?>> INSERT_ADDRESS_FIELDS = List.of(
            DSL.field(COL_STREET),
            DSL.field(COL_CITY_SUBDIVISION),
            DSL.field(COL_CITY),
            DSL.field(COL_CITY_SUPERDIVISION),
            DSL.field(COL_COUNTRY_CODE),
            DSL.field(COL_POSTAL_CODE),
            DSL.field(COL_TIMEZONE)
    );
    private static final String INSERT_ADDRESS_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_ADDRESS_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_ADDRESS_FIELDS.size()))
            .getSQL();

    @Override
    public int create(Address addressObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_ADDRESS_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, addressObj.getStreet());
            SQLUtils.setStringOrNull(ps, 2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            SQLUtils.setStringOrNull(ps, 4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            SQLUtils.setStringOrNull(ps, 6, addressObj.getPostalCode());
            SQLUtils.setStringOrNull(ps, 7, addressObj.getTimezone());

            SQLUtils.updateAndSetGeneratedId(ps, addressObj::setAddressId);

            return addressObj.getAddressId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }


    /*
        INSERT INTO person_addresses (person_info_id, address_id)
            VALUES (
            #{personAddressObj.personInfoId},
            #{personAddressObj.addressId}
            )
    */

    private static final List<Field<?>> INSERT_PERSON_ASSOCIATION_FIELDS = List.of(
            DSL.field(PersonInfoDAO.COL_PERSON_INFO_ID),
            DSL.field(COL_ADDRESS_ID)
    );

    private static final String INSERT_PERSON_ASSOCIATION_SQL = create
            .insertInto(DSL.table(PERSON_ADDRESSES_TABLE_NAME), INSERT_PERSON_ASSOCIATION_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_ASSOCIATION_FIELDS.size()))
            .getSQL();

    @Override
    public void createPersonAssociation(int personInfoId, int addressId) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PERSON_ASSOCIATION_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setInt(1, personInfoId);
            ps.setInt(2, addressId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT * FROM addresses WHERE address_id = #{addressId}
    */
    private static final String SELECT_ADDRESS_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_ADDRESS_ID))
            .getSQL();

    @Override
    public Address getById(int addressId) {
        Address address = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_ADDRESS_BY_ID_SQL)
        ) {
            ps.setInt(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = extractAddressFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return address;
    }


    /*
        SELECT * FROM addresses
        WHERE street = #{street}
        AND city = #{city}
        AND postal_code = #{postalCode}
        AND country_code = #{countryCode}
    */
    private static final String SELECT_ADDRESS_BY_UNIQUE_FIELDS_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_STREET))
            .and(SQLUtils.eqPlaceholder(COL_CITY))
            .and(SQLUtils.eqPlaceholder(COL_POSTAL_CODE))
            .and(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();

    @Override
    public Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode) {
        Address address = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_ADDRESS_BY_UNIQUE_FIELDS_SQL)
        ) {
            ps.setString(1, street);
            ps.setString(2, city);
            ps.setString(3, postalCode);
            ps.setString(4, countryCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    address = extractAddressFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return address;
    }

        /*
            UPDATE addresses SET
            street = #{addressObj.street},
            city_subdivision = #{addressObj.citySubdivision},
            city = #{addressObj.city},
            city_superdivision = #{addressObj.citySuperdivision},
            country_code = #{addressObj.countryCode},
            postal_code = #{addressObj.postalCode},
            timezone = #{addressObj.timezone}
            WHERE address_id = #{addressObj.addressId}
        */

    private static final String UPDATE_ADDRESS_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_STREET), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CITY_SUBDIVISION), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CITY), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_CITY_SUPERDIVISION), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_COUNTRY_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_POSTAL_CODE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_TIMEZONE), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_ADDRESS_ID))
            .getSQL();

    @Override
    public void update(Address addressObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_ADDRESS_SQL)
        ) {
            ps.setString(1, addressObj.getStreet());
            SQLUtils.setStringOrNull(ps, 2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            SQLUtils.setStringOrNull(ps, 4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            SQLUtils.setStringOrNull(ps, 6, addressObj.getPostalCode());
            SQLUtils.setStringOrNull(ps, 7, addressObj.getTimezone());

            ps.setInt(8, addressObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        DELETE FROM addresses WHERE address_id = #{addressId}
    */

    private static final String DELETE_ADDRESS_SQL = create
            .delete(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_ADDRESS_ID))
            .getSQL();


    @Override
    public void delete(int addressId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement statement = conn.prepareStatement(DELETE_ADDRESS_SQL)
        ) {
            statement.setInt(1, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT pi.*
        FROM person_info pi
        JOIN person_addresses pa
        ON pi.person_info_id = pa.person_info_id
        WHERE pa.address_id = #{addressId}
     */
    private static final String SELECT_PEOPLE_BY_ADDRESS_ID_SQL = create
            .select()
            .from(DSL.table(PersonInfoDAO.TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.PERSON_ADDRESSES_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.PA_EXPLICIT_COL_PERSON_INFO_ID)
            )
            .where(SQLUtils.eqPlaceholder(PersonInfoDAO.PA_EXPLICIT_COL_ADDRESS_ID))
            .getSQL();

    @Override
    public Set<PersonInfo> getPeopleByAddressId(int addressId) {
        Set<PersonInfo> people = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_PEOPLE_BY_ADDRESS_ID_SQL)
        ) {
            ps.setInt(1, addressId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PersonInfo personInfo = extractPersonInfoFromResultSet(rs);

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

    private Address extractAddressFromResultSet(ResultSet rs) {
        try {
            Address address = new Address();
            address.setAddressId(rs.getInt(COL_ADDRESS_ID));
            address.setStreet(rs.getString(COL_STREET));
            address.setCitySubdivision(rs.getString(COL_CITY_SUBDIVISION));
            address.setCity(rs.getString(COL_CITY));
            address.setCitySuperdivision(rs.getString(COL_CITY_SUPERDIVISION));
            address.setCountryCode(rs.getString(COL_COUNTRY_CODE));
            address.setPostalCode(rs.getString(COL_POSTAL_CODE));
            address.setTimezone(rs.getString(COL_TIMEZONE));

            Set<PersonInfo> people = getPeopleByAddressId(address.getAddressId());
            address.setPeopleWithAddress(people);

            return address;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Address", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Address" + e);
        }
    }

    private static PersonInfo extractPersonInfoFromResultSet(ResultSet rs) throws SQLException {
        try {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setPersonInfoId(rs.getInt(PersonInfoDAO.COL_PERSON_INFO_ID));
            personInfo.setSurname(rs.getString(PersonInfoDAO.COL_SURNAME));
            personInfo.setGivenName(rs.getString(PersonInfoDAO.COL_GIVEN_NAME));
            personInfo.setMiddleName(rs.getString(PersonInfoDAO.COL_MIDDLE_NAME));
            personInfo.setBirthdate(rs.getDate(PersonInfoDAO.COL_BIRTHDATE));
            personInfo.setSex(rs.getString(PersonInfoDAO.COL_SEX));
            return personInfo;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of PersonInfo", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of PersonInfo" + e);
        }
    }
}
