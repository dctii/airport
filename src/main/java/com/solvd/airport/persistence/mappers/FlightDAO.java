package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.Flight;

public interface FlightDAO {
    void createFlight(Flight flight);
    Flight getFlightByCode(String flightCode);
    void updateFlight(Flight flight);
    void deleteFlight(String flightCode);
}
