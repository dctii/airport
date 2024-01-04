package com.solvd.airport.persistence;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface BaggageDAO {
    void createBaggage(@Param("baggageObj") Baggage baggageObj);

    Baggage getBaggageByCode(@Param("baggageCode") String baggageCode);

    void updateBaggage(@Param("baggageObj") Baggage baggageObj);

    void deleteBaggage(@Param("baggageCode") String baggageCode);

    void updateBaggageWithBoardingPassId(
            @Param("baggageCode") String baggageCode,
            @Param("boardingPassId") int boardingPassId
    );


    String TABLE_NAME = "baggage";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_BAGGAGE_CODE = "baggage_code";
    String COL_WEIGHT = "weight";
    String COL_PRICE = "price";
    String COL_CHECK_IN_ID = CheckInDAO.COL_CHECK_IN_ID;
    String EXPLICIT_COL_BAGGAGE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BAGGAGE_CODE);
    String EXPLICIT_COL_WEIGHT = SQLUtils.qualifyColumnName(TABLE_NAME, COL_WEIGHT);
    String EXPLICIT_COL_PRICE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PRICE);
    String EXPLICIT_COL_CHECK_IN_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CHECK_IN_ID);

}
