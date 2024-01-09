package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PersonEmailAddressDAO;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class AirlineStaffMemberJDBCImpl implements AirlineStaffMemberDAO {
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);

    @Override
    public void createAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_STAFF_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, staffMemberObj.getMemberRole());
            ps.setInt(2, staffMemberObj.getPersonInfoId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating airline staff member failed, no rows affected.");
            }

            SQLUtils.setGeneratedKey(ps, staffMemberObj::setAirlineStaffId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AirlineStaffMember getAirlineStaffMemberById(int airlineStaffId) {
        AirlineStaffMember airlineStaffMember = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            ps.setInt(1, airlineStaffId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlineStaffMember;
    }

    @Override
    public AirlineStaffMember findByPersonInfoId(int personInfoId) {
        AirlineStaffMember airlineStaffMember = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_PERSON_INFO_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlineStaffMember;
    }

    @Override
    public AirlineStaffMember findByEmailAddress(String emailAddress) {
        AirlineStaffMember airlineStaffMember = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL_ADDRESS_SQL)
        ) {
            ps.setString(1, emailAddress);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airlineStaffMember;
    }


    @Override
    public void updateAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_STAFF_SQL)
        ) {
            ps.setString(1, staffMemberObj.getMemberRole());
            ps.setInt(2, staffMemberObj.getPersonInfoId());
            ps.setInt(3, staffMemberObj.getAirlineStaffId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAirlineStaffMember(int airlineStaffId) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_STAFF_SQL)
        ) {
            ps.setInt(1, airlineStaffId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static AirlineStaffMember extractAirlineStaffMemberFromResultSet(ResultSet rs) throws SQLException {
        AirlineStaffMember airlineStaffMember = new AirlineStaffMember();
        airlineStaffMember.setAirlineStaffId(rs.getInt(COL_AIRLINE_STAFF_ID));
        airlineStaffMember.setMemberRole(rs.getString(COL_MEMBER_ROLE));
        airlineStaffMember.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));

        return airlineStaffMember;
    }

        /*
        "INSERT INTO airline_staff (member_role, person_info_id) VALUES (?, ?)";
    */

    private static final List<Field<?>> INSERT_AIRLINE_STAFF_FIELDS = List.of(
            DSL.field(COL_MEMBER_ROLE),
            DSL.field(COL_PERSON_INFO_ID)
    );
    private static final String INSERT_AIRLINE_STAFF_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_STAFF_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_STAFF_FIELDS.size()))
            .getSQL();

    /*
         "SELECT * FROM airline_staff WHERE airline_staff_id = ?";
     */

    private static final String FIND_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();

    /*
        "SELECT * FROM airline_staff WHERE person_info_id = ?";
     */

    private static final String FIND_BY_PERSON_INFO_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();
    /*
        "UPDATE airline_staff SET member_role = ?, person_info_id = ? WHERE airline_staff_id = ?";
     */

    private static final String UPDATE_AIRLINE_STAFF_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_MEMBER_ROLE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PERSON_INFO_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();

    /*
        "DELETE FROM airline_staff WHERE airline_staff_id = ?";
     */

    private static final String DELETE_AIRLINE_STAFF_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();


    /*
        "SELECT a.* FROM airline_staff a " +
            "JOIN person_info p ON a.person_info_id = p.person_info_id " +
            "JOIN person_email_addresses pea ON p.person_info_id = pea.person_info_id " +
            "JOIN email_addresses e ON pea.email_address_id = e.email_address_id " +
        "WHERE e.email_address = ?";
     */

    private static final String FIND_BY_EMAIL_ADDRESS_SQL = create
            .select(DSL.field(ALL_COLUMNS))
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID
                    )
            )
            .join(DSL.table(PersonEmailAddressDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID,
                            PersonEmailAddressDAO.EXPLICIT_COL_PERSON_INFO_ID
                    )
            )
            .join(DSL.table(EmailAddressDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonEmailAddressDAO.COL_EMAIL_ADDRESS_ID,
                            EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS_ID
                    )
            )
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS))
            .getSQL();
}
