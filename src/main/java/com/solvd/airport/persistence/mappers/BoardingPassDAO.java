package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.BoardingPass;

public interface BoardingPassDAO {
    void createBoardingPass(BoardingPass boardingPass);
    BoardingPass getBoardingPassById(int id);
    BoardingPass getBoardingPassByCheckInId(int checkInId);
    void updateBoardingPass(BoardingPass boardingPass);
    void deleteBoardingPass(int id);

}
