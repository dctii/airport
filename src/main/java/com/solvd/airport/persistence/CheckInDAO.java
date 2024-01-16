package com.solvd.airport.persistence;

import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface CheckInDAO extends AbstractDAO<CheckIn> {
    int create(@Param("checkInObj") CheckIn checkInObj);

    CheckIn getById(@Param("checkInId") int checkInId);

    CheckIn getCheckInByBookingNumber(@Param("bookingNumber") String bookingNumber);

    void update(@Param("checkInObj") CheckIn checkInObj);

    void delete(@Param("checkInId") int checkInId);

    boolean hasCheckInForBookingId(@Param("bookingId") int bookingId);


    String TABLE_NAME = "check_ins";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_CHECK_IN_ID = "check_in_id";
    String COL_CHECK_IN_METHOD = "check_in_method";
    String COL_PASS_ISSUED = "pass_issued";
    String COL_AIRLINE_STAFF_ID = AirlineStaffMemberDAO.COL_AIRLINE_STAFF_ID;
    String COL_BOOKING_ID = BookingDAO.COL_BOOKING_ID;
    String EXPLICIT_COL_CHECK_IN_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CHECK_IN_ID);
    String EXPLICIT_COL_CHECK_IN_METHOD = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CHECK_IN_METHOD);
    String EXPLICIT_COL_PASS_ISSUED = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PASS_ISSUED);
    String EXPLICIT_COL_AIRLINE_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_STAFF_ID);
    String EXPLICIT_COL_BOOKING_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BOOKING_ID);

    String CHECK_IN_METHOD_STAFF = "staff";
    String CHECK_IN_METHOD_SELF = "self";
}
