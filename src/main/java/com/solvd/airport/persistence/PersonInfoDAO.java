package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDAO {

    PersonInfo findByName(@Param("surname") String surname, @Param("givenName") String givenName);
    void createPersonInfo(@Param("personInfoObj") PersonInfo personInfoObj);
    PersonInfo getPersonInfoById(@Param("personInfoId") int personInfoId);
    void updatePersonInfo(@Param("personInfoObj") PersonInfo personInfoObj);
    void deletePersonInfo(@Param("personInfoId") int personInfoId);
}
