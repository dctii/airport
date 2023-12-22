package com.solvd.airport.util;

import com.solvd.airport.db.DBConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public class SQLUtils {
    // utils for SQL-related actions, like SQL statement generations, etc.
    private static final Logger LOGGER = LogManager.getLogger(SQLUtils.class);

    public static void displayBoardingPassInfo(String bookingNumber) {
        DBConnectionPool connectionPool = DBConnectionPool.getInstance();

        final String GET_BOARDING_PASS_INFO_SQL =
                "SELECT bp.baggage_id, bp.boarding_group, bp.boarding_time, f.flight_code, g.gate_code "
                        + "FROM boarding_passes AS bp "
                        + "JOIN flights AS f ON bp.flight_id = f.flight_code "
                        + "JOIN gates AS g ON f.departure_gate_id = g.gate_id "
                        + "JOIN check_ins AS ci ON bp.check_in_id = ci.check_in_id "
                        + "JOIN bookings AS b ON ci.booking_id = b.booking_id "
                        + "WHERE b.booking_number = ?";


        try (
                Connection conn = connectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(GET_BOARDING_PASS_INFO_SQL)
        ) {
            statement.setString(1, bookingNumber);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String boardingGroup = rs.getString("boarding_group");
                    Timestamp boardingTime = rs.getTimestamp("boarding_time");
                    String flightId = rs.getString("flight_code");
                    String gateCode = rs.getString("gate_code");
                    String baggageId = rs.getString("baggage_id");

                    // Now you can display the information however you need.
                    LOGGER.info("Boarding Group: {}\n, Boarding Time: {}\n, Flight: {}\n, Gate: {}\n, Baggage ID: {}\n",
                            baggageId, boardingGroup, boardingTime, flightId, gateCode);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error fetching boarding pass information", e);
        }
    }

    private SQLUtils() {
        ExceptionUtils.preventUtilityInstantiation();
    }
}
