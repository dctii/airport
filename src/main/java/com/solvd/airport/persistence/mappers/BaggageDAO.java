package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.Baggage;

public interface BaggageDAO {
    void createBaggage(Baggage baggage);
    Baggage getBaggageByCode(String code);
    void updateBaggage(Baggage baggage);
    void deleteBaggage(String code);
    void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId);

}
