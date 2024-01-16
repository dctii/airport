package com.solvd.airport.service;

import com.solvd.airport.domain.Gate;

public interface UpdateFlightGateService {

    void updateFlightGate(String flightCode, Gate gate);
}
