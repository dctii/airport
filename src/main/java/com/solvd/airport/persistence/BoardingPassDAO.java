package com.solvd.airport.persistence;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface BoardingPassDAO {
    void createBoardingPass(@Param("boardingPassObj") BoardingPass boardingPassObj);

    BoardingPass getBoardingPassById(@Param("boardingPassId") int boardingPassId);

    BoardingPass getBoardingPassByCheckInId(@Param("checkInId") int checkInId);

    void updateBoardingPass(@Param("boardingPassObj") BoardingPass boardingPassObj);

    void deleteBoardingPass(@Param("boardingPassId") int boardingPassId);


    String TABLE_NAME = "boarding_passes";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_BOARDING_PASS_ID = "boarding_pass_id";
    String COL_IS_BOARDED = "is_boarded";
    String COL_BOARDING_TIME = "boarding_time";
    String COL_BOARDING_GROUP = "boarding_group";
    String COL_CHECK_IN_ID = CheckInDAO.COL_CHECK_IN_ID;
    String EXPLICIT_COL_BOARDING_PASS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOARDING_PASS_ID);
    String EXPLICIT_COL_IS_BOARDED = SQLUtils.qualifyColumnName(TABLE_NAME, COL_IS_BOARDED);
    String EXPLICIT_COL_BOARDING_TIME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOARDING_TIME);
    String EXPLICIT_COL_BOARDING_GROUP = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOARDING_GROUP);
    String EXPLICIT_COL_CHECK_IN_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CHECK_IN_ID);

}
