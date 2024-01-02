package com.solvd.airport.persistence;

import com.solvd.airport.domain.BoardingPass;
import org.apache.ibatis.annotations.Param;

public interface BoardingPassDAO {
    void createBoardingPass(@Param("boardingPassObj") BoardingPass boardingPassObj);
    BoardingPass getBoardingPassById(@Param("boardingPassId") int boardingPassId);
    BoardingPass getBoardingPassByCheckInId(@Param("checkInId") int checkInId);
    void updateBoardingPass(@Param("boardingPassObj") BoardingPass boardingPassObj);
    void deleteBoardingPass(@Param("boardingPassId") int boardingPassId);

}
