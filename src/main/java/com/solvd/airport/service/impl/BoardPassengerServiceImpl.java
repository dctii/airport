package com.solvd.airport.service.impl;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.JacksonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardPassengerServiceImpl implements BoardPassengerService {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOARD_PASSENGER_SERVICE_IMPL);
    private final BoardingPassDAO boardingPassDAO = DataAccessProvider.getBoardingPassDAO();
    private final CheckInDAO checkInDAO = DataAccessProvider.getCheckInDAO();
    private final BookingDAO bookingDAO = DataAccessProvider.getBookingDAO();

    @Override
    public boolean boardPassenger(String bookingNumber) {
        boolean wasBoardingSuccessful = false;
        Booking booking = JacksonUtils.getBookingByBookingNumber(bookingNumber);
        if (doesExistWithBookingNumber(bookingNumber, booking)) {
            return wasBoardingSuccessful;
        }

        CheckIn checkIn = checkInDAO.getCheckInByBookingNumber(bookingNumber);
        if (doesExistWithBookingNumber(bookingNumber, checkIn)) {
            return wasBoardingSuccessful;
        }

        BoardingPass boardingPass = boardingPassDAO.getBoardingPassByCheckInId(checkIn.getCheckInId());
        if (doesExistWithBookingNumber(bookingNumber, boardingPass)) {
            return wasBoardingSuccessful;
        }

        boardingPass.setBoarded(true);
        boardingPassDAO.update(boardingPass);

        booking.setStatus(BookingDAO.STATUS_BOARDED);
        bookingDAO.update(booking);
        JacksonUtils.updateBookingByBookingNumber(booking);

        // display booking and boarding pass info for boarding confirmation
        LOGGER.info("Boarding Successful for Booking Number: {}", bookingNumber);
        LOGGER.info("Flight Code: {}", booking.getFlightCode());
        LOGGER.info("Status: {}", booking.getStatus());
        LOGGER.info("Boarded: {}", boardingPass.isBoarded() ? "Yes" : "No");

        return true;
    }

    private static boolean doesExistWithBookingNumber(String bookingNumber, Object bookingNumAssociation) {
        boolean doesExist = false;
        if (bookingNumAssociation == null) {
            LOGGER.error("No association found with booking number: " + bookingNumber);
            doesExist = true;
        }
        return doesExist;
    }
}
