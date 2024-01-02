package com.solvd.airport.util;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.exception.InvalidDateFormatException;
import com.solvd.airport.persistence.CountryDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.IntConsumer;


public class SQLUtils {
    private static final Logger LOGGER = LogManager.getLogger(SQLUtils.class);

    public static void setGeneratedKey(PreparedStatement preparedStatement, IntConsumer setIdConsumer) throws SQLException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                setIdConsumer.accept(id);
            } else {
                throw new SQLException("Failed to obtain ID.");
            }
        }
    }
    public static void displayBoardingPassInfo(String bookingNumber, boolean hasBaggage) {
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();

        final String GET_BOARDING_PASS_INFO_SQL =
                "SELECT bp.boarding_group, bp.boarding_time, f.flight_code, g.gate_code" +
                        (hasBaggage ? ", ba.baggage_code " : " ") +
                        "FROM boarding_passes AS bp " +
                        "JOIN check_ins AS ci ON bp.check_in_id = ci.check_in_id " +
                        "JOIN bookings AS b ON ci.booking_id = b.booking_id " +
                        "JOIN flights AS f ON b.flight_code = f.flight_code " +
                        "JOIN gates AS g ON f.gate_id = g.gate_id" +
                        (hasBaggage ? " LEFT JOIN baggage AS ba ON ci.check_in_id = ba.check_in_id " : " ") +
                        "WHERE b.booking_number = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BOARDING_PASS_INFO_SQL)) {
            ps.setString(1, bookingNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String boardingGroup = rs.getString("boarding_group");
                    Timestamp boardingTime = rs.getTimestamp("boarding_time");
                    String flightCode = rs.getString("flight_code");
                    String gateCode = rs.getString("gate_code");
                    String baggageInfo = hasBaggage ? rs.getString("baggage_code") : "No Baggage";

                    LOGGER.info("\n\n=== Boarding Information ===\n" +
                                    "Boarding Group: {}\n" +
                                    "Boarding Time: {}\n" +
                                    "Flight Code: {}\n" +
                                    "Gate: {}\n" +
                                    (hasBaggage ? "Baggage Code: {}" : "{}"),
                            boardingGroup, boardingTime, flightCode, gateCode,
                            baggageInfo + "\n"
                    );
                } else {
                    LOGGER.info("No boarding information found for booking number: {}", bookingNumber);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching boarding pass information", e);
        }
    }


    public static String determineBoardingGroup(String seatClass) {
        seatClass = seatClass.toLowerCase();
        switch (seatClass) {
            case "first class":
                return "Group A";
            case "business":
                return "Group B";
            case "economy":
                return "Group C";
            default:
                return "Unknown Group";
        }
    }

    public static java.sql.Timestamp toTimestamp(String datetimeString) {
        java.sql.Timestamp newTimestamp;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(StringConstants.TIMESTAMP_PATTERN);
            java.util.Date parsedDate = formatter.parse(datetimeString);
            newTimestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Invalid timestamp format: " + e);
        }
        return newTimestamp;
    }

    public static java.sql.Date toDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(StringConstants.YEAR_FIRST_DATE_PATTERN);
            java.util.Date parsedDate = formatter.parse(dateString);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new InvalidDateFormatException("Invalid date format: " + e.getMessage());
        }
    }

    public static boolean doesCountryCodeExist(String countryCode) {
        final CountryDAO countryDAO = DataAccessProvider.getCountryDAO();

        boolean exists = countryDAO.doesCountryCodeExist(countryCode);

        if (!exists) {
            LOGGER.error("Country code doesn't exist in DB, returning false.");
        }

        return exists;
    }


    private SQLUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
