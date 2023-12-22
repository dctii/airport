package com.solvd.airport.service.impl;

import com.solvd.airport.domain.*;
import com.solvd.airport.persistence.dao.*;
import com.solvd.airport.persistence.impl.*;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CheckInServiceImpl implements CheckInService {
    private static final Logger LOGGER = LogManager.getLogger(CheckInServiceImpl.class);
    private final CheckInDAO checkInDAO = new CheckInDAOImpl();
    private final BaggageDAO baggageDAO = new BaggageDAOImpl();
    private final BoardingPassDAO boardingPassDAO = new BoardingPassDAOImpl();
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final PersonInfoDAO personInfoDAO = new PersonInfoDAOImpl();
    private final AirportStaffMemberDAO airportStaffMemberDAO = new AirportStaffMemberDAOImpl();

    @Override
    public void performCheckIn(String bookingNumber, String flightId) {
        LOGGER.info(bookingNumber);
        LOGGER.info(flightId);

        // Find Booking
        Booking booking = bookingDAO.findByBookingNumber(bookingNumber);
        LOGGER.info(booking);

        // Find Airport Staff Member for check-in
        PersonInfo personInfo = personInfoDAO.findByName("Doe", "Jane");
        AirportStaffMember staffMember = airportStaffMemberDAO.findByPersonInfoId(personInfo.getPersonInfoId());
        LOGGER.info(staffMember);

        // Perform check-in
        CheckIn checkIn = new CheckIn(
                "staff",
                staffMember.getAirportStaffId(),
                booking.getBookingId()
        );
        checkInDAO.insertCheckIn(checkIn);

        // Handle baggage
        Baggage baggage = new Baggage(
                "DL123-A001-K12-20241220",
                new BigDecimal("25.00"),
                new BigDecimal("0.00"));
        baggageDAO.insertBaggage(baggage);


        // TODO: Stops here. Need to debug
        // Create boarding pass
        BoardingPass boardingPass = new BoardingPass(
                Timestamp.valueOf("2024-12-20 08:00:00"),
                "Group 3",
                checkIn.getCheckInId(),
                flightId,
                baggage.getBaggageCode()
        );
        boardingPassDAO.insertBoardingPass(boardingPass);

        // Update baggage with boarding pass ID
        baggage.setBoardingPassId(boardingPass.getBoardingPassId());
        baggageDAO.updateBaggageWithBoardingPassId(
                baggage.getBaggageCode(),
                boardingPass.getBoardingPassId()
        );

        // Update booking status
        booking.setFlightId(flightId);
        booking.setBookingStatus("checked-in");
        bookingDAO.updateBooking(booking);

        // Mark check-in as completed (issuing boarding pass)
        checkIn.setPassIssued(true);
        checkInDAO.updateCheckIn(checkIn);

        SQLUtils.displayBoardingPassInfo(bookingNumber);

    }
}
