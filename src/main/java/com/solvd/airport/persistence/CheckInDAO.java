package com.solvd.airport.persistence;

import com.solvd.airport.domain.CheckIn;
import org.apache.ibatis.annotations.Param;

public interface CheckInDAO {
    void createCheckIn(@Param("checkInObj") CheckIn checkInObj);
    CheckIn getCheckInById(@Param("checkInId") int checkInId);
    CheckIn getCheckInByBookingNumber(@Param("bookingNumber") String bookingNumber);
    void updateCheckIn(@Param("checkInObj") CheckIn checkInObj);
    void deleteCheckIn(@Param("checkInId") int checkInId);

    boolean hasCheckInForBookingId(@Param("bookingId") int bookingId);
}
