package com.solvd.airport.persistence;

import com.solvd.airport.domain.Baggage;
import org.apache.ibatis.annotations.Param;

public interface BaggageDAO {
    void createBaggage(@Param("baggageObj") Baggage baggageObj);
    Baggage getBaggageByCode(@Param("baggageCode") String baggageCode);
    void updateBaggage(@Param("baggageObj") Baggage baggageObj);
    void deleteBaggage(@Param("baggageCode") String baggageCode);
    void updateBaggageWithBoardingPassId(@Param("baggageCode") String baggageCode, @Param("boardingPassId") int boardingPassId);

}
