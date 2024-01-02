package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonPhoneNumber;
import org.apache.ibatis.annotations.Param;

public interface PersonPhoneNumberDAO {
    void createPersonPhoneNumber(@Param("personPhoneNumberObj") PersonPhoneNumber personPhoneNumberObj);
    PersonPhoneNumber getPersonPhoneNumberById(@Param("personInfoId") int personInfoId, @Param("phoneNumberId") int phoneNumberId);
    void updatePersonPhoneNumber(@Param("personPhoneNumberObj") PersonPhoneNumber personPhoneNumberObj);
    void deletePersonPhoneNumber(@Param("personInfoId") int personInfoId, @Param("phoneNumberId") int phoneNumberId);
}
