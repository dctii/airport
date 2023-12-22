package com.solvd.airport.persistence.dao;

import com.solvd.airport.domain.CheckIn;

public interface CheckInDAO {
    void insertCheckIn(CheckIn checkIn);
    void updateCheckIn(CheckIn checkIn);

}
