package com.solvd.airport.service.impl;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.service.UpdateFlightGateService;
import com.solvd.airport.util.AnsiCodes;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.DataAccessProvider;
import com.solvd.airport.util.JaxbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateFlightGateServiceImpl implements UpdateFlightGateService  {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.UPDATE_FLIGHT_GATE_SERVICE_IMPL);
    private final FlightDAO flightDAO = DataAccessProvider.getFlightDAO();

    @Override
    public void updateFlightGate(String flightCode, Gate gate) {
        Flight flight = flightDAO.getFlightByCode(flightCode);

        // Check if the provided Gate exists in the XML file
        Gate gateFromXml = JaxbUtils.getGateByCodes(gate.getGateCode(), gate.getAirportCode());

        if (gateFromXml != null) {
            flight.setGateId(gateFromXml.getGateId());
            flightDAO.update(flight);

            LOGGER.info("{}Flight {} gate updated successfully to: {}{}",
                    AnsiCodes.GREEN, flight.getFlightCode(), gate.getGateCode(), AnsiCodes.RESET_ALL);
        } else {
            LOGGER.error("Gate not found for code: {}", gate.getGateCode());
        }
    }
}
