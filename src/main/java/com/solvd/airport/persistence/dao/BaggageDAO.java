package com.solvd.airport.persistence.dao;

import com.solvd.airport.domain.Baggage;

public interface BaggageDAO {
    void insertBaggage(Baggage baggage);
    void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId);

}
