package com.solvd.airport.persistence.jdbc;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.exception.UnsuccessfulDeleteException;
import com.solvd.airport.exception.UnsuccessfulInstanceCreationException;
import com.solvd.airport.exception.UnsuccessfulResultSetExtractionException;
import com.solvd.airport.exception.UnsuccessfulUpdateException;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.PassportDAO;
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

public class PassportJDBCImpl implements PassportDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PASSPORT_JDBC_IMPL);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();
    private static final DSLContext create = DSL.using(SQLConstants.SQL_DIALECT);

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

    @Override
    public int create(Passport passportObj) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_PASSPORT_SQL,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            ps.setString(1, passportObj.getPassportNumber());
            ps.setDate(2, passportObj.getIssueDate());
            ps.setDate(3, passportObj.getExpiryDate());
            SQLUtils.setIntOrNull(ps, 4, passportObj.getPersonInfoId());

            SQLUtils.updateAndSetGeneratedId(ps, passportObj::setPassportId);
        } catch (SQLException e) {
            throw new UnsuccessfulInstanceCreationException("Error creating passport" + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    /*
        SELECT * FROM passports
        WHERE passport_id = #{passportId}
     */
    private static final String GET_PASSPORT_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_ID))
            .getSQL();


    @Override
    public Passport getById(int passportId) {
        Passport passport = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(GET_PASSPORT_BY_ID_SQL)
        ) {
            ps.setInt(1, passportId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    passport = extractPassportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new UnsuccessfulInstanceCreationException("Error retrieving passport with id " + passportId + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return passport;
    }

    /*
        "SELECT * FROM passports WHERE passport_number = ?";
     */
    private static final String GET_PASSPORT_BY_PASSPORT_NUMBER_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_NUMBER))
            .getSQL();

    @Override
    public Passport getPassportByPassportNumber(String passportNumber) {
        Passport passport = null;

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(GET_PASSPORT_BY_PASSPORT_NUMBER_SQL)
        ) {
            ps.setString(1, passportNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    passport = extractPassportFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new UnsuccessfulInstanceCreationException("Error retrieving passport with number " + passportNumber + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }

        return passport;
    }

    /*
        "UPDATE passports SET issue_date = ?, expiry_date = ?, person_info_id = ? WHERE passport_number = ?";
    */
    private static final String UPDATE_PASSPORT_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_PASSPORT_NUMBER), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_ISSUE_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_EXPIRY_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PERSON_INFO_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_ID))
            .getSQL();

    @Override
    public void update(Passport passportObj) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_PASSPORT_SQL)
        ) {
            ps.setString(1, passportObj.getPassportNumber());
            ps.setDate(2, passportObj.getIssueDate());
            ps.setDate(3, passportObj.getExpiryDate());
            SQLUtils.setIntOrNull(ps, 4, passportObj.getPersonInfoId());

            ps.setInt(5, passportObj.getPassportId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UnsuccessfulUpdateException("Error updating passport with number " + passportObj.getPassportId() + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        "UPDATE passports SET passport_number = ?, issue_date = ?, expiry_date = ?, person_info_id = ? WHERE passport_id = ?";
    */
    private static final String UPDATE_PASSPORT_BY_PASSPORT_NUMBER_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_ISSUE_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_EXPIRY_DATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_PERSON_INFO_ID), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_NUMBER))
            .getSQL();

    @Override
    public void updatePassportByPassportNumber(Passport passportObj) {
        Connection conn = connectionPool.getConnection();

        try (
                PreparedStatement ps = conn.prepareStatement(UPDATE_PASSPORT_BY_PASSPORT_NUMBER_SQL)
        ) {
            ps.setDate(1, passportObj.getIssueDate());
            ps.setDate(2, passportObj.getExpiryDate());
            SQLUtils.setIntOrNull(ps, 3, passportObj.getPersonInfoId());

            ps.setString(4, passportObj.getPassportNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UnsuccessfulUpdateException("Error updating passport with number " + passportObj.getPassportNumber() + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
    "DELETE FROM passports WHERE passport_id = ?";
 */
    private static final String DELETE_PASSPORT_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_ID))
            .getSQL();

    @Override
    public void delete(int passportId) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_PASSPORT_SQL)
        ) {
            ps.setInt(1, passportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UnsuccessfulDeleteException("Error deleting passport with number " + passportId + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

        /*
        "DELETE FROM passports WHERE passport_number = ?";
     */

    private static final String DELETE_PASSPORT_BY_PASSPORT_NUMBER_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_NUMBER))
            .getSQL();

    @Override
    public void deletePassportByPassportNumber(String passportNumber) {
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DELETE_PASSPORT_BY_PASSPORT_NUMBER_SQL)
        ) {
            ps.setString(1, passportNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UnsuccessfulDeleteException("Error deleting passport with number " + passportNumber + e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    /*
        SELECT COUNT(*) > 0 FROM passports
        WHERE passport_number = #{passportNumber}
     */
    private static final String DOES_PASSPORT_EXIST_SQL = create
            .selectCount()
            .from(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PASSPORT_NUMBER))
            .getSQL();

    @Override
    public boolean doesPassportExist(String passportNumber) {
        boolean doesExist = false;
        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(DOES_PASSPORT_EXIST_SQL)
        ) {
            ps.setString(1, passportNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doesExist = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return doesExist;
    }

    /*
        SELECT b.*
        FROM bookings b
        WHERE b.passport_number = #{passportNumber}
     */

    private static final String SELECT_BOOKING_BY_PASSPORT_NUMBER_SQL = create
            .select(DSL.field(BookingDAO.ALL_COLUMNS))
            .from(DSL.table(BookingDAO.TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(BookingDAO.EXPLICIT_COL_PASSPORT_NUMBER))
            .getSQL();

    @Override
    public Set<Booking> getBookingsByPassportNumber(String passportNumber) {
        Set<Booking> bookings = new HashSet<>();

        Connection conn = connectionPool.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(SELECT_BOOKING_BY_PASSPORT_NUMBER_SQL)
        ) {
            ps.setString(1, passportNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = extractBookingFromResultSet(rs);

                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return bookings;
    }

    private Passport extractPassportFromResultSet(ResultSet rs) {
        try {
            Passport passport = new Passport();
            passport.setPassportId(rs.getInt(COL_PASSPORT_ID));
            passport.setPassportNumber(rs.getString(COL_PASSPORT_NUMBER));
            passport.setIssueDate(rs.getDate(COL_ISSUE_DATE));
            passport.setExpiryDate(rs.getDate(COL_EXPIRY_DATE));
            passport.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));

            Set<Booking> bookings = getBookingsByPassportNumber(passport.getPassportNumber());
            passport.setBookings(bookings);

            return passport;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Passport", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Passport" + e);
        }
    }

    private Booking extractBookingFromResultSet(ResultSet rs) {
        try {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt(BookingDAO.COL_BOOKING_ID));
            booking.setBookingNumber(rs.getString(BookingDAO.COL_BOOKING_NUMBER));
            booking.setPurchaseDatetime(rs.getTimestamp(BookingDAO.COL_PURCHASE_DATETIME));
            booking.setSeatClass(rs.getString(BookingDAO.COL_SEAT_CLASS));
            booking.setSeatNumber(rs.getString(BookingDAO.COL_SEAT_NUMBER));
            booking.setStatus(rs.getString(BookingDAO.COL_STATUS));
            booking.setPrice(rs.getBigDecimal(BookingDAO.COL_PRICE));
            booking.setAgency(rs.getString(BookingDAO.COL_AGENCY));
            booking.setPassportNumber(rs.getString(BookingDAO.COL_PASSPORT_NUMBER));
            booking.setFlightCode(rs.getString(BookingDAO.COL_FLIGHT_CODE));
            return booking;
        } catch (SQLException e) {
            LOGGER.info("SQLException occurred. Unsuccessful creation of Booking", e);
            throw new UnsuccessfulResultSetExtractionException("SQLException occurred. Unsuccessful creation of Booking" + e);
        }
    }


}
