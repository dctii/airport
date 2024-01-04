package com.solvd.airport.persistence;

import com.solvd.airport.domain.Passport;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PassportDAO {
    void createPassport(@Param("passportObj") Passport passportObj);

    Passport getPassportById(@Param("passportNumber") String passportNumber);

    void updatePassport(@Param("passportObj") Passport passportObj);

    void deletePassport(@Param("passportNumber") String passportNumber);

// TODO:   boolean doesPassportExist(@Param("passportNumber") String passportNumber);

    String TABLE_NAME = "passports";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PASSPORT_NUMBER = "passport_number";
    String COL_ISSUE_DATE = "issue_date";
    String COL_EXPIRY_DATE = "expiry_date";
    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String EXPLICIT_COL_PASSPORT_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PASSPORT_NUMBER);
    String EXPLICIT_COL_ISSUE_DATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ISSUE_DATE);
    String EXPLICIT_COL_EXPIRY_DATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EXPIRY_DATE);
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);


}
