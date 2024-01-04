package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;

public class EmailAddressDAOImpl implements EmailAddressDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


    @Override
    public void createEmailAddress(EmailAddress emailAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_EMAIL_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, emailAddressObj.getEmailAddress());
            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, emailAddressObj::setEmailAddressId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EmailAddress getEmailAddressById(int emailAddressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_ID_SQL)
        ) {
            ps.setInt(1, emailAddressId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractEmailAddressFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EmailAddress getEmailAddressByEmail(String email) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_EMAIL_BY_EMAIL_ADDRESS_SQL)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractEmailAddressFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void updateEmailAddress(EmailAddress emailAddressObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_SQL)
        ) {
            ps.setString(1, emailAddressObj.getEmailAddress());
            ps.setInt(2, emailAddressObj.getEmailAddressId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmailAddress(int emailAddressId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_EMAIL_SQL)
        ) {
            ps.setInt(1, emailAddressId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static EmailAddress extractEmailAddressFromResultSet(ResultSet rs) throws SQLException {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmailAddressId(rs.getInt(COL_EMAIL_ADDRESS_ID));
        emailAddress.setEmailAddress(rs.getString(COL_EMAIL_ADDRESS));
        return emailAddress;
    }

        /*
        "INSERT INTO email_addresses (email_address) VALUES (?)";
    */

    private static final String INSERT_EMAIL_SQL = create
            .insertInto(DSL.table(EmailAddressDAO.TABLE_NAME), DSL.field(EmailAddressDAO.COL_EMAIL_ADDRESS))
            .values(SQLConstants.PLACEHOLDER)
            .getSQL();

    /*
        "SELECT * FROM email_addresses WHERE email_address_id = ?";
     */

    private static final String SELECT_EMAIL_BY_ID_SQL = create
            .selectFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    /*
        "SELECT * FROM email_addresses WHERE email_address = ?";
     */

    private static final String SELECT_EMAIL_BY_EMAIL_ADDRESS_SQL = create
            .selectFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    /*
        "UPDATE email_addresses SET email_address = ? WHERE email_address_id = ?";
     */

    private static final String UPDATE_EMAIL_SQL = create
            .update(DSL.table(EmailAddressDAO.TABLE_NAME))
            .set(DSL.field(EmailAddressDAO.COL_EMAIL_ADDRESS), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

    /*
        "DELETE FROM email_addresses WHERE email_address_id = ?";
     */

    private static final String DELETE_EMAIL_SQL = create
            .deleteFrom(DSL.table(EmailAddressDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.COL_EMAIL_ADDRESS_ID))
            .getSQL();

}
