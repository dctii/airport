package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDAO {

    PersonInfo findByName(
            @Param("surname") String surname,
            @Param("givenName") String givenName
    );

    void createPersonInfo(@Param("personInfoObj") PersonInfo personInfoObj);

    PersonInfo getPersonInfoById(@Param("personInfoId") int personInfoId);

    void updatePersonInfo(@Param("personInfoObj") PersonInfo personInfoObj);

    void deletePersonInfo(@Param("personInfoId") int personInfoId);

    String TABLE_NAME = "person_info";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PERSON_INFO_ID = "person_info_id";
    String COL_SURNAME = "surname";
    String COL_GIVEN_NAME = "given_name";
    String COL_MIDDLE_NAME = "middle_name";
    String COL_BIRTHDATE = "birthdate";
    String COL_SEX = "sex";
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
    String EXPLICIT_COL_SURNAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SURNAME);
    String EXPLICIT_COL_GIVEN_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_GIVEN_NAME);
    String EXPLICIT_COL_MIDDLE_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_MIDDLE_NAME);
    String EXPLICIT_COL_BIRTHDATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BIRTHDATE);
    String EXPLICIT_COL_SEX = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SEX);
}
