package com.solvd.airport.service.impl;

import com.solvd.airport.domain.*;
import com.solvd.airport.persistence.mappers.*;
import com.solvd.airport.persistence.impl.*;
import com.solvd.airport.service.CheckInService;
import com.solvd.airport.util.SQLUtils;
import com.solvd.airport.util.StringFormatters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CheckInServiceImpl implements CheckInService {
    private final CheckInDAO checkInDAO = new CheckInDAOImpl();
    private final BaggageDAO baggageDAO = new BaggageDAOImpl();
    private final BoardingPassDAO boardingPassDAO = new BoardingPassDAOImpl();
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final PersonInfoDAO personInfoDAO = new PersonInfoDAOImpl();
    private final PersonEmailAddressDAO personEmailAddressDAO = new PersonEmailAddressDAOImpl();
    private final AirlineStaffMemberDAO airlineStaffMemberDAO = new AirlineStaffMemberDAOImpl();
    private final FlightDAO flightDAO = new FlightDAOImpl();

    @Override
    public void performCheckIn(String staffEmailString, String bookingNumber, boolean hasBaggage, double baggageWeight) {
        // Step 1: Find Booking
        Booking booking = bookingDAO.findByBookingNumber(bookingNumber);
        String flightCode = booking.getFlightCode();
        Flight flight = flightDAO.getFlightByCode(flightCode);

        // Step 2: Find Staff by Email and Assign for Check-In
        PersonEmailAddress staffEmailAddress = personEmailAddressDAO.getPersonEmailAddressByEmail(staffEmailString);
        PersonInfo staffInfo = personInfoDAO.getPersonInfoById(staffEmailAddress.getPersonInfoId());
        AirlineStaffMember staffMember = airlineStaffMemberDAO.findByPersonInfoId(staffInfo.getPersonInfoId());

        // Step 3: Perform Check-In
        boolean checkInExists = checkInDAO.hasCheckInForBookingId(booking.getBookingId());
        if (checkInExists) {
            // TODO: set a handling method for this that will reroute the interface user to type in a new booking number or to exit
            throw new IllegalStateException("Check-in already exists for booking number: " + bookingNumber);
        }
        CheckIn checkIn = new CheckIn(
                "staff",
                true,
                staffMember.getAirlineStaffId(),
                booking.getBookingId()
        );
        checkInDAO.createCheckIn(checkIn);

        // Step 4: Handle Baggage if applicable
        if (hasBaggage) {
            String destinationAirportCode = flight.getDestination();
            String baggageCode = StringFormatters.createBaggageCode(
                    flightCode,
                    destinationAirportCode,
                    bookingNumber
            );

            BigDecimal baggagePrice = BigDecimal.valueOf(0.00);
            if (baggageWeight > 20.00) {
                baggagePrice = BigDecimal.valueOf(baggageWeight * 1.05).setScale(2, RoundingMode.HALF_UP);
            }
            Baggage baggage = new Baggage(
                    baggageCode,
                    BigDecimal.valueOf(baggageWeight),
                    BigDecimal.valueOf(baggageWeight).setScale(2, RoundingMode.HALF_UP),
                    checkIn.getCheckInId()
            );
            baggageDAO.createBaggage(baggage);
        }

        // Step 5: Create Boarding Pass
        LocalDateTime departureTime = flight.getDepartureTime().toLocalDateTime();
        String boardingGroup = SQLUtils.determineBoardingGroup(booking.getSeatClass());
        Timestamp boardingTime = Timestamp.valueOf(departureTime.minusMinutes(30));

        BoardingPass boardingPass = new BoardingPass(
                false,
                boardingTime,
                boardingGroup,
                checkIn.getCheckInId()
        );
        boardingPassDAO.createBoardingPass(boardingPass);

        // Step 6: Update Booking Status
        booking.setStatus("Checked-in");
        bookingDAO.updateBooking(booking);

        // Step 7: Finalize Check-In
        checkIn.setPassIssued(true);
        checkInDAO.updateCheckIn(checkIn);

        // Display boarding pass info (if needed)
        SQLUtils.displayBoardingPassInfo(bookingNumber, hasBaggage);
    }
}
