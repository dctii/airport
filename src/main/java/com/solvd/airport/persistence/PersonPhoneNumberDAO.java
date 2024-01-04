package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonPhoneNumber;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PersonPhoneNumberDAO {
    void createPersonPhoneNumber(@Param("personPhoneNumberObj") PersonPhoneNumber personPhoneNumberObj);

    PersonPhoneNumber getPersonPhoneNumberById(@Param("personInfoId") int personInfoId, @Param("phoneNumberId") int phoneNumberId);

    void updatePersonPhoneNumber(@Param("personPhoneNumberObj") PersonPhoneNumber personPhoneNumberObj);

    void deletePersonPhoneNumber(@Param("personInfoId") int personInfoId, @Param("phoneNumberId") int phoneNumberId);

    String TABLE_NAME = "person_phone_numbers";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String COL_PHONE_NUMBER_ID = PhoneNumberDAO.COL_PHONE_NUMBER_ID;
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
    String EXPLICIT_COL_PHONE_NUMBER_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PHONE_NUMBER_ID);
}
