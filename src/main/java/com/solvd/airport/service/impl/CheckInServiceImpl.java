package com.solvd.airport.service.impl;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.domain.Baggage;
import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.domain.Flight;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;
import com.solvd.airport.persistence.BaggageDAO;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.util.BigDecimalUtils;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.JacksonUtils;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StaxUtils;
import com.solvd.airport.util.StringFormatters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckInServiceImpl implements CheckInService {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.CHECK_IN_SERVICE_IMPL);
    private final CheckInDAO checkInDAO = DataAccessProvider.getCheckInDAO();
    private final BaggageDAO baggageDAO = DataAccessProvider.getBaggageDAO();
    private final BoardingPassDAO boardingPassDAO = DataAccessProvider.getBoardingPassDAO();
    private final BookingDAO bookingDAO = DataAccessProvider.getBookingDAO();
    private final AirlineStaffMemberDAO airlineStaffMemberDAO = DataAccessProvider.getAirlineStaffMemberDAO();
    private final FlightDAO flightDAO = DataAccessProvider.getFlightDAO();

    @Override
    public void performCheckIn(String staffEmailString, String bookingNumber, boolean hasBaggage, double baggageWeight) {
        // Step 1: Find Booking
        Booking booking = JacksonUtils.getBookingByBookingNumber(bookingNumber);
        Flight flight = flightDAO.getFlightByCode(booking.getFlightCode());

        AirlineStaffMember checkInStaffMember = StaxUtils.getAirlineStaffMemberByEmail(staffEmailString); // to use StAX
        // airlineStaffMemberDAO.getByEmailAddress(staffEmailString); // to use MyBatisImpl or JDBCImpl
        if (checkInStaffMember == null) {
            LOGGER.error("Try again. Staff member not found for email: {}", staffEmailString);
            return;
        }

        // Step 3: Perform Check-In
        if (doesCheckInExist(bookingNumber, booking)) {
            return;
        }

        CheckIn checkIn = new CheckIn(
                CheckInDAO.CHECK_IN_METHOD_STAFF,
                true,
                checkInStaffMember.getAirlineStaffId(),
                booking.getBookingId()
        );
        checkInDAO.create(checkIn);

        // Step 4: Handle Baggage if applicable
        if (hasBaggage) {
            String baggageCode = StringFormatters.createBaggageCode(
                    booking.getFlightCode(),
                    flight.getDestination(),
                    bookingNumber
            );

            Baggage baggage = new Baggage(
                    baggageCode,
                    BigDecimal.valueOf(baggageWeight),
                    calculateBaggagePrice(baggageWeight),
                    checkIn.getCheckInId()
            );

            baggageDAO.create(baggage);
        }

        // Step 5: Create Boarding Pass

        BoardingPass boardingPass = new BoardingPass(
                false,
                calculateBoardingTime(flight),
                SQLUtils.determineBoardingGroup(booking.getSeatClass()),
                checkIn.getCheckInId()
        );
        boardingPassDAO.create(boardingPass);

        // Step 6: Update Booking Status
        updateBookingStatus(booking, BookingDAO.STATUS_CHECKED_IN);

        // Step 7: Finalize Check-In
        updateCheckInBoardingPassIssueStatus(checkIn, true);

        // Display boarding pass info (if needed)
        SQLUtils.displayBoardingPassInfo(bookingNumber, hasBaggage);
    }

    private static Timestamp calculateBoardingTime(Flight flight) {
        LocalDateTime departureTime = flight.getDepartureTime().toLocalDateTime();
        Timestamp boardingTime = Timestamp.valueOf(departureTime.minusMinutes(30));
        return boardingTime;
    }

    private void updateCheckInBoardingPassIssueStatus(CheckIn checkIn, boolean isBoardingPassIssued) {
        checkIn.setPassIssued(isBoardingPassIssued);
        checkInDAO.update(checkIn);
    }

    private void updateBookingStatus(Booking booking, String newStatus) {
        booking.setStatus(newStatus);
        bookingDAO.update(booking);
        JacksonUtils.updateBookingByBookingNumber(booking);
    }

    private boolean doesCheckInExist(String bookingNumber, Booking booking) {
        boolean checkInExists = checkInDAO.hasCheckInForBookingId(booking.getBookingId());
        if (checkInExists) {
            LOGGER.error("Try again. Check-in already exists for booking number: " + bookingNumber);
            return true;
        }
        return false;
    }

    private static BigDecimal calculateBaggagePrice(double baggageWeight) {
        BigDecimal baggagePrice;
        if (baggageWeight > BaggageDAO.BAGGAGE_WEIGHT_THRESHOLD_BEFORE_COST_FACTORED) {
            baggagePrice = BigDecimalUtils.round(
                    2,
                    baggageWeight * BaggageDAO.BAGGAGE_WEIGHT_POST_THRESHOLD_COST_FACTOR
            );
        } else {
            baggagePrice = BigDecimalUtils.round(
                    2,
                    baggageWeight
            );
        }
        return baggagePrice;
    }
}
