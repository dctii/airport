package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.Address;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;


public class AddressJDBCImpl implements AddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createAddress(Address addressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_ADDRESS_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, addressObj.getStreet());
            ps.setString(2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            ps.setString(4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            ps.setString(6, addressObj.getPostalCode());
            ps.setString(7, addressObj.getTimezone());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, addressObj::setAddressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        Address address = null;

        try (
                Connection conn = connectionPool.getConnection();
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
        }

        return address;
    }


    @Override
    public Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode) {
        Address address = null;

        try (
                Connection conn = connectionPool.getConnection();
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
        }
        return address;
    }

    @Override
    public void updateAddress(Address addressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_ADDRESS_SQL)
        ) {
            ps.setString(1, addressObj.getStreet());
            ps.setString(2, addressObj.getCitySubdivision());
            ps.setString(3, addressObj.getCity());
            ps.setString(4, addressObj.getCitySuperdivision());
            ps.setString(5, addressObj.getCountryCode());
            ps.setString(6, addressObj.getPostalCode());
            ps.setString(7, addressObj.getTimezone());
            ps.setInt(8, addressObj.getAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(DELETE_ADDRESS_SQL)
        ) {
            statement.setInt(1, addressId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Address extractAddressFromResultSet(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getInt(COL_ADDRESS_ID));
        address.setStreet(rs.getString(COL_STREET));
        address.setCitySubdivision(rs.getString(COL_CITY_SUBDIVISION));
        address.setCity(rs.getString(COL_CITY));
        address.setCitySuperdivision(rs.getString(COL_CITY_SUPERDIVISION));
        address.setCountryCode(rs.getString(COL_COUNTRY_CODE));
        address.setPostalCode(rs.getString(COL_POSTAL_CODE));
        address.setTimezone(rs.getString(COL_TIMEZONE));

        return address;
    }

    /*
        "INSERT INTO addresses " +
        "(street, city_subdivision, city, city_superdivision, country_code, postal_code, timezone) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
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

    /*
        "SELECT * FROM addresses WHERE address_id = ?";
    */
    private static final String SELECT_ADDRESS_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_ADDRESS_ID))
            .getSQL();

    /*
        "SELECT * FROM addresses WHERE street = ? AND city = ? AND postal_code = ? AND country_code = ?";
    */
    private static final String SELECT_ADDRESS_BY_UNIQUE_FIELDS_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_STREET))
            .and(SQLUtils.eqPlaceholder(COL_CITY))
            .and(SQLUtils.eqPlaceholder(COL_POSTAL_CODE))
            .and(SQLUtils.eqPlaceholder(COL_COUNTRY_CODE))
            .getSQL();


    /*
        "UPDATE addresses SET " +
        "street = ?, city_subdivision = ?, city = ?, city_superdivision = ?, country_code = ?, " +
        "postal_code = ?, timezone = ? WHERE address_id = ?";
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

    /*
        "DELETE FROM addresses WHERE address_id = ?";
    */

    private static final String DELETE_ADDRESS_SQL = create
            .delete(DSL.table(TABLE_NAME))
            .where(DSL.field(COL_ADDRESS_ID).eq(SQLConstants.PLACEHOLDER))
            .getSQL();
}
