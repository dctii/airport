package com.solvd.airport.persistence;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PassportDAO extends AbstractDAO<Passport> {
    int create(@Param("passportObj") Passport passportObj);

    Passport getById(@Param("passportId") int passportId);

    Passport getPassportByPassportNumber(@Param("passportNumber") String passportNumber);

    void update(@Param("passportObj") Passport passportObj);

    void updatePassportByPassportNumber(@Param("passportObj") Passport passportObj);

    void delete(@Param("passportId") int passportId);

    void deletePassportByPassportNumber(@Param("passportNumber") String passportNumber);

    boolean doesPassportExist(@Param("passportNumber") String passportNumber);

    Set<Booking> getBookingsByPassportNumber(@Param("passportNumber") String passportNumber);

    String TABLE_NAME = "passports";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PASSPORT_ID = "passport_id";
    String COL_PASSPORT_NUMBER = "passport_number";
    String COL_ISSUE_DATE = "issue_date";
    String COL_EXPIRY_DATE = "expiry_date";
    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String EXPLICIT_COL_PASSPORT_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PASSPORT_NUMBER);
    String EXPLICIT_COL_ISSUE_DATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ISSUE_DATE);
    String EXPLICIT_COL_EXPIRY_DATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EXPIRY_DATE);
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);


}
