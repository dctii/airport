package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.PersonEmailAddress;

public interface PersonEmailAddressDAO {
    void createPersonEmailAddress(PersonEmailAddress personEmailAddress);
    PersonEmailAddress getPersonEmailAddressById(int personInfoId, int emailAddressId);
    PersonEmailAddress getPersonEmailAddressByEmail(String staffEmail);
    void updatePersonEmailAddress(PersonEmailAddress personEmailAddress);
    void deletePersonEmailAddress(int personInfoId, int emailAddressId);

}
