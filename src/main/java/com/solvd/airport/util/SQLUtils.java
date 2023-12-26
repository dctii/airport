package com.solvd.airport.util;

import com.solvd.airport.db.DBConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.function.IntConsumer;


public class SQLUtils {
    // utils for SQL-related actions, like SQL statement generations, etc.
    private static final Logger LOGGER = LogManager.getLogger(SQLUtils.class);

    public static void setGeneratedKey(PreparedStatement statement, IntConsumer setIdConsumer) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
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
             PreparedStatement statement = conn.prepareStatement(GET_BOARDING_PASS_INFO_SQL)) {
            statement.setString(1, bookingNumber);
            try (ResultSet rs = statement.executeQuery()) {
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


    private SQLUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
