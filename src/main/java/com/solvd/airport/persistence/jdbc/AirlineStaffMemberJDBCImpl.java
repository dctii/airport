package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.util.DataAccessProvider;
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
import java.util.List;

public class AirlineStaffMemberJDBCImpl implements AirlineStaffMemberDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRLINE_STAFF_MEMBER_JDBC_IMPL);

    private static final PersonInfoDAO personInfoDAO = DataAccessProvider.getPersonInfoDAO();
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);


    /*
        INSERT INTO airline_staff (member_role, person_info_id)
        VALUES (
        #{airlineStaffMemberObj.memberRole},
        #{airlineStaffMemberObj.personInfoId}
        )
    */

    private static final List<Field<?>> INSERT_AIRLINE_STAFF_FIELDS = List.of(
            DSL.field(COL_MEMBER_ROLE),
            DSL.field(COL_PERSON_INFO_ID)
    );
    private static final String INSERT_AIRLINE_STAFF_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_AIRLINE_STAFF_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_AIRLINE_STAFF_FIELDS.size()))
            .getSQL();

    @Override
    public int create(AirlineStaffMember airlineStaffMemberObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_AIRLINE_STAFF_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, airlineStaffMemberObj.getMemberRole());
            SQLUtils.setIntOrNull(ps, 2, getPersonInfoId(airlineStaffMemberObj));

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating airline staff member failed, no rows affected.");
            }

            SQLUtils.setGeneratedKey(ps, airlineStaffMemberObj::setAirlineStaffId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

        /*
         SELECT * FROM airline_staff
        WHERE airline_staff_id = #{airlineStaffId}
     */

    private static final String SELECT_BY_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();

    @Override
    public AirlineStaffMember getById(int airlineStaffId) {
        AirlineStaffMember airlineStaffMember = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)
        ) {
            ps.setInt(1, airlineStaffId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return airlineStaffMember;
    }

        /*
        SELECT * FROM airline_staff
        WHERE person_info_id = #{personInfoId}
     */

    private static final String SELECT_BY_PERSON_INFO_ID_SQL = create
            .select()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    @Override
    public AirlineStaffMember getByPersonInfoId(int personInfoId) {
        AirlineStaffMember airlineStaffMember = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BY_PERSON_INFO_ID_SQL)
        ) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return airlineStaffMember;
    }

        /*
        SELECT s.* FROM airline_staff AS s
        JOIN person_info p
        ON s.person_info_id = p.person_info_id
        JOIN person_email_addresses pea
        ON p.person_info_id = pea.person_info_id
        JOIN email_addresses e
        ON pea.email_address_id = e.email_address_id
        WHERE e.email_address = #{emailAddress}
     */

    private static final String SELECT_BY_EMAIL_ADDRESS_SQL = create
            .select(DSL.field(ALL_COLUMNS))
            .from(DSL.table(TABLE_NAME))
            .join(DSL.table(PersonInfoDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID
                    )
            )
            .join(DSL.table(PersonInfoDAO.PERSON_EMAIL_ADDRESSES_TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.EXPLICIT_COL_PERSON_INFO_ID,
                            PersonInfoDAO.PEA_EXPLICIT_COL_PERSON_INFO_ID
                    )
            )
            .join(DSL.table(EmailAddressDAO.TABLE_NAME))
            .on(
                    SQLUtils.eqFields(
                            PersonInfoDAO.PEA_EXPLICIT_COL_EMAIL_ADDRESS_ID,
                            EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS_ID
                    )
            )
            .where(SQLUtils.eqPlaceholder(EmailAddressDAO.EXPLICIT_COL_EMAIL_ADDRESS))
            .getSQL();

    @Override
    public AirlineStaffMember getByEmailAddress(String emailAddress) {
        AirlineStaffMember airlineStaffMember = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL_ADDRESS_SQL)
        ) {
            ps.setString(1, emailAddress);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    airlineStaffMember = extractAirlineStaffMemberFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return airlineStaffMember;
    }


    /*
        UPDATE airline_staff
        SET
        member_role = #{airlineStaffMemberObj.memberRole},
        person_info_id = #{airlineStaffMemberObj.personInfoId}
        WHERE airline_staff_id = #{airlineStaffMemberObj.airlineStaffId}
     */

    private static final String UPDATE_AIRLINE_STAFF_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_MEMBER_ROLE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PERSON_INFO_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();

    @Override
    public void update(AirlineStaffMember airlineStaffMemberObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_STAFF_SQL)
        ) {
            ps.setString(1, airlineStaffMemberObj.getMemberRole());
            SQLUtils.setIntOrNull(ps, 2, getPersonInfoId(airlineStaffMemberObj));

            ps.setInt(3, airlineStaffMemberObj.getAirlineStaffId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

        /*
        DELETE FROM airline_staff
        WHERE airline_staff_id = #{airlineStaffId}
     */

    private static final String DELETE_AIRLINE_STAFF_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_AIRLINE_STAFF_ID))
            .getSQL();


    @Override
    public void delete(int airlineStaffId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_STAFF_SQL)
        ) {
            ps.setInt(1, airlineStaffId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    private static AirlineStaffMember extractAirlineStaffMemberFromResultSet(ResultSet rs) {
        try {
            AirlineStaffMember airlineStaffMember = new AirlineStaffMember();
            airlineStaffMember.setAirlineStaffId(rs.getInt(COL_AIRLINE_STAFF_ID));
            airlineStaffMember.setMemberRole(rs.getString(COL_MEMBER_ROLE));


            int personInfoId = rs.getInt(COL_PERSON_INFO_ID);
            if (rs.wasNull()) {
                airlineStaffMember.setPersonInfo(null);
            } else {
                PersonInfo personInfo = personInfoDAO.getById(personInfoId);
                airlineStaffMember.setPersonInfo(personInfo);
            }

            return airlineStaffMember;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of AirlineStaffMember", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of AirlineStaffMember" + e);
        }
    }

    private static Integer getPersonInfoId(AirlineStaffMember airlineStaffMemberObj) {
        return airlineStaffMemberObj.getPersonInfo() != null
                ? airlineStaffMemberObj.getPersonInfo().getPersonInfoId()
                : null;
    }

}
