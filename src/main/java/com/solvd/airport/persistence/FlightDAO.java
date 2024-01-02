package com.solvd.airport.persistence;

import com.solvd.airport.domain.Flight;
import org.apache.ibatis.annotations.Param;

public interface FlightDAO {
    void createFlight(@Param("flightObj") Flight flightObj);
    Flight getFlightByCode(@Param("flightCode") String flightCode);
    void updateFlight(@Param("flightObj") Flight flightObj);
    void deleteFlight(@Param("flightCode") String flightCode);
}
