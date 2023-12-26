package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.PersonPhoneNumber;

public interface PersonPhoneNumberDAO {
    void createPersonPhoneNumber(PersonPhoneNumber personPhoneNumber);
    PersonPhoneNumber getPersonPhoneNumberById(int personInfoId, int phoneNumberId);
    void updatePersonPhoneNumber(PersonPhoneNumber personPhoneNumber);
    void deletePersonPhoneNumber(int personInfoId, int phoneNumberId);
}
