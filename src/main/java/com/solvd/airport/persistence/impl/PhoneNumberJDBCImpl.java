package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.PhoneNumberDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class PhoneNumberJDBCImpl implements PhoneNumberDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createPhoneNumber(PhoneNumber phoneNumberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_PHONE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            ps.setString(2, phoneNumberObj.getExtension());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, phoneNumberObj::setPhoneNumberId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(int phoneNumberId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_PHONE_BY_ID_SQL)
        ) {

            ps.setInt(1, phoneNumberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPhoneNumberFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PhoneNumber getPhoneNumberByNumber(String phoneNumber) {
        PhoneNumber phoneNumberObj = null;
        try (
                Connection conn = connectionPool.getConnection();
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
        }
        return phoneNumberObj;
    }


    @Override
    public void updatePhoneNumber(PhoneNumber phoneNumberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_PHONE_SQL)
        ) {
            ps.setString(1, phoneNumberObj.getPhoneNumber());
            ps.setString(2, phoneNumberObj.getExtension());
            ps.setInt(3, phoneNumberObj.getPhoneNumberId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePhoneNumber(int phoneNumberId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_PHONE_SQL)
        ) {
            ps.setInt(1, phoneNumberId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PhoneNumber extractPhoneNumberFromResultSet(ResultSet rs) throws SQLException {
        PhoneNumber phoneNumberObj = new PhoneNumber();
        phoneNumberObj.setPhoneNumberId(rs.getInt(COL_PHONE_NUMBER_ID));
        phoneNumberObj.setPhoneNumber(rs.getString(COL_PHONE_NUMBER));
        phoneNumberObj.setExtension(rs.getString(COL_EXTENSION));

        return phoneNumberObj;
    }

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

    /*
        "SELECT * FROM phone_numbers WHERE phone_number_id = ?";
     */

    private static final String SELECT_PHONE_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();

    /*
        "SELECT * FROM phone_numbers WHERE phone_number = ?";
     */

    private static final String SELECT_PHONE_BY_PHONE_NUMBER_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER))
            .getSQL();

    /*
        "UPDATE phone_numbers SET phone_number = ?, extension = ? WHERE phone_number_id = ?";
     */

    private static final String UPDATE_PHONE_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PHONE_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_EXTENSION), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();

    /*
        "DELETE FROM phone_numbers WHERE phone_number_id = ?";
    */
    private static final String DELETE_PHONE_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PHONE_NUMBER_ID))
            .getSQL();
}
