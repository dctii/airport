package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonEmailAddress;
import org.apache.ibatis.annotations.Param;

public interface PersonEmailAddressDAO {
    void createPersonEmailAddress(@Param("personEmailAddressObj") PersonEmailAddress personEmailAddressObj);
    PersonEmailAddress getPersonEmailAddressById(@Param("personInfoId") int personInfoId, @Param("emailAddressId") int emailAddressId);
    PersonEmailAddress getPersonEmailAddressByEmail(@Param("emailAddress") String emailAddress);
    void updatePersonEmailAddress(@Param("personEmailAddressObj") PersonEmailAddress personEmailAddressObj);
    void deletePersonEmailAddress(@Param("personInfoId") int personInfoId, @Param("emailAddressId") int emailAddressId);

}
