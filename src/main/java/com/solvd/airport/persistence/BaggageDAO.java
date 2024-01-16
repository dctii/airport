package com.solvd.airport.persistence;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface BaggageDAO extends AbstractDAO<Baggage> {
    int create(@Param("baggageObj") Baggage baggageObj);

    Baggage getById(@Param("baggageId") int baggageId);

    Baggage getBaggageByCode(@Param("baggageCode") String baggageCode);

    void update(@Param("baggageObj") Baggage baggageObj);

    void updateBaggageByCode(@Param("baggageObj") Baggage baggageObj);


    void delete(@Param("baggageId") int baggageId);

    void deleteBaggageByCode(@Param("baggageCode") String baggageCode);


    String TABLE_NAME = "baggage";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_BAGGAGE_ID = "baggage_id";
    String COL_BAGGAGE_CODE = "baggage_code";
    String COL_WEIGHT = "weight";
    String COL_PRICE = "price";
    String COL_CHECK_IN_ID = CheckInDAO.COL_CHECK_IN_ID;
    String EXPLICIT_COL_BAGGAGE_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BAGGAGE_CODE);
    String EXPLICIT_COL_WEIGHT = SQLUtils.qualifyColumnName(TABLE_NAME, COL_WEIGHT);
    String EXPLICIT_COL_PRICE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PRICE);
    String EXPLICIT_COL_CHECK_IN_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CHECK_IN_ID);

    double BAGGAGE_WEIGHT_THRESHOLD_BEFORE_COST_FACTORED = 20.00;
    double BAGGAGE_WEIGHT_POST_THRESHOLD_COST_FACTOR = 1.05;

}
