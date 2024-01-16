package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PhoneNumberDAO extends ContactDAO<PhoneNumber> {
    int create(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);

    PhoneNumber getById(@Param("phoneNumberId") int phoneNumberId);

    PhoneNumber getPhoneNumberByNumber(@Param("phoneNumber") String phoneNumber);

    void update(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);

    void delete(@Param("phoneNumberId") int phoneNumberId);

    Set<PersonInfo> getPeopleByPhoneNumberId(@Param("phoneNumberId") int phoneNumberId);


    String TABLE_NAME = "phone_numbers";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_PHONE_NUMBER_ID = "phone_number_id";
    String COL_PHONE_NUMBER = "phone_number";
    String COL_EXTENSION = "extension";
    String EXPLICIT_COL_PHONE_NUMBER_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PHONE_NUMBER_ID);
    String EXPLICIT_COL_PHONE_NUMBER = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PHONE_NUMBER);
    String EXPLICIT_COL_EXTENSION = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EXTENSION);

    String PERSON_PHONE_NUMBER_TABLE_NAME = PersonInfoDAO.PERSON_PHONE_NUMBER_TABLE_NAME;
}
