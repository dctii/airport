package com.solvd.airport.persistence;

import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PhoneNumberDAO {
    void createPhoneNumber(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);
    PhoneNumber getPhoneNumberById(@Param("phoneNumberId") int phoneNumberId);
    PhoneNumber getPhoneNumberByNumber(@Param("phoneNumber") String phoneNumber);
    void updatePhoneNumber(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);
    void deletePhoneNumber(@Param("phoneNumberId") int phoneNumberId);


    String TABLE_NAME = "phone_numbers";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_PHONE_NUMBER_ID = "phone_number_id";
    String COL_PHONE_NUMBER = "phone_number";
    String COL_EXTENSION = "extension";
    String EXPLICIT_COL_PHONE_NUMBER_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PHONE_NUMBER_ID);
    String EXPLICIT_COL_PHONE_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PHONE_NUMBER);
    String EXPLICIT_COL_EXTENSION = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EXTENSION);


}
