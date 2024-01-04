package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.persistence.PassportDAO;
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

public class PassportDAOImpl implements PassportDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createPassport(Passport passportObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_PASSPORT_SQL)) {
            ps.setString(1, passportObj.getPassportNumber());
            ps.setDate(2, passportObj.getIssueDate());
            ps.setDate(3, passportObj.getExpiryDate());
            ps.setInt(4, passportObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating passport", e);
        }
    }

    @Override
    public Passport getPassportById(String passportNumber) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_PASSPORT_BY_ID_SQL)
        ) {
            ps.setString(1, passportNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractPassportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving passport with number " + passportNumber, e);
        }
        return null;
    }


    @Override
    public void updatePassport(Passport passportObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_PASSPORT_SQL)
        ) {
            ps.setDate(1, passportObj.getIssueDate());
            ps.setDate(2, passportObj.getExpiryDate());
            ps.setInt(3, passportObj.getPersonInfoId());
            ps.setString(4, passportObj.getPassportNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating passport with number " + passportObj.getPassportNumber(), e);
        }
    }

    @Override
    public void deletePassport(String passportNumber) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_PASSPORT_SQL)
        ) {
            ps.setString(1, passportNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting passport with number " + passportNumber, e);
        }
    }

    private static Passport extractPassportFromResultSet(ResultSet rs) throws SQLException {
        Passport passport = new Passport();
        passport.setPassportNumber(rs.getString(COL_PASSPORT_NUMBER));
        passport.setIssueDate(rs.getDate(COL_ISSUE_DATE));
        passport.setExpiryDate(rs.getDate(COL_EXPIRY_DATE));
        passport.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));

        return passport;
    }

        /*
        "INSERT INTO passports (passport_number, issue_date, expiry_date, person_info_id) VALUES (?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_PASSPORT_FIELDS = List.of(
            DSL.field(COL_PASSPORT_NUMBER),
            DSL.field(COL_ISSUE_DATE),
            DSL.field(COL_EXPIRY_DATE),
            DSL.field(COL_PERSON_INFO_ID)
    );

    private static final String INSERT_PASSPORT_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PASSPORT_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PASSPORT_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM passports WHERE passport_number = ?";
     */
    private static final String GET_PASSPORT_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_NUMBER))
            .getSQL();

    /*
        "UPDATE passports SET issue_date = ?, expiry_date = ?, person_info_id = ? WHERE passport_number = ?";
     */
    private static final String UPDATE_PASSPORT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_ISSUE_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_EXPIRY_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PERSON_INFO_ID), SQLConstants.PLACEHOLDER)
            .where(COL_PASSPORT_NUMBER)
            .getSQL();

    /*
        "DELETE FROM passports WHERE passport_number = ?";
     */

    private static final String DELETE_PASSPORT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(COL_PASSPORT_NUMBER)
            .getSQL();
}
