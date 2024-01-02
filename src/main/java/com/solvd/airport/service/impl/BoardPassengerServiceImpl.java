package com.solvd.airport.service.impl;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.exception.NoBoardingPassFoundException;
import com.solvd.airport.exception.NoBookingException;
import com.solvd.airport.exception.NoCheckInException;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.service.BoardPassengerService;
import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.DataAccessProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardPassengerServiceImpl implements BoardPassengerService {
    private static final Logger LOGGER = LogManager.getLogger(BoardPassengerServiceImpl.class);
    private final BoardingPassDAO boardingPassDAO = DataAccessProvider.getBoardingPassDAO();
    private final CheckInDAO checkInDAO = DataAccessProvider.getCheckInDAO();
    private final BookingDAO bookingDAO = DataAccessProvider.getBookingDAO();

    @Override
    public void boardPassenger(String bookingNumber) {
        LOGGER.info("{}Parameter 'bookingNumber': " + bookingNumber + "{}", AnsiCodes.RED, AnsiCodes.RESET_ALL);
        Booking booking = bookingDAO.findByBookingNumber(bookingNumber);
        if (booking == null) {
            LOGGER.error("No booking found with booking number: " + bookingNumber);
            throw new NoBookingException("No booking found with booking number: " + bookingNumber);
        }

        CheckIn checkIn = checkInDAO.getCheckInByBookingNumber(bookingNumber);
        if (checkIn == null) {
            LOGGER.error("No Check In found for booking number: " + bookingNumber);
            throw new NoCheckInException("No Check In found for booking number: " + bookingNumber);
        }

        BoardingPass boardingPass = boardingPassDAO.getBoardingPassByCheckInId(checkIn.getCheckInId());
        if (boardingPass == null) {
            LOGGER.error("No boarding pass found for booking number: " + bookingNumber);
            throw new NoBoardingPassFoundException("No boarding pass found for booking number: " + bookingNumber);
        }

        boardingPass.setBoarded(true);
        boardingPassDAO.updateBoardingPass(boardingPass);

        booking.setStatus("Boarded");
        bookingDAO.updateBooking(booking);

        // display booking and boarding pass info for boarding confirmation
        LOGGER.info("Boarding Successful for Booking Number: {}", bookingNumber);
        LOGGER.info("Flight Code: {}", booking.getFlightCode());
        LOGGER.info("Status: {}", booking.getStatus());
        LOGGER.info("Boarded: {}", boardingPass.isBoarded() ? "Yes" : "No");
    }
}
