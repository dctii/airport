package com.solvd.airport.service.impl;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.mappers.BoardingPassDAO;
import com.solvd.airport.persistence.mappers.BookingDAO;
import com.solvd.airport.persistence.impl.BoardingPassDAOImpl;
import com.solvd.airport.persistence.impl.BookingDAOImpl;
import com.solvd.airport.service.BoardPassengerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardPassengerServiceImpl implements BoardPassengerService {
    private static final Logger LOGGER = LogManager.getLogger(BoardPassengerServiceImpl.class);
    private final BoardingPassDAO boardingPassDAO = new BoardingPassDAOImpl();
    private final BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public void boardPassenger(String bookingNumber) {
        Booking booking = bookingDAO.findByBookingNumber(bookingNumber);
        if (booking == null) {
            LOGGER.error("No booking found with booking number: " + bookingNumber);
            return;
        }

        BoardingPass boardingPass = boardingPassDAO.getBoardingPassByCheckInId(booking.getBookingId());
        if (boardingPass == null) {
            LOGGER.error("No boarding pass found for booking number: " + bookingNumber);
            return;
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
