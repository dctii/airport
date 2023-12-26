package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.PersonAddress;

public interface PersonAddressDAO {
    void createPersonAddress(PersonAddress personAddress);
    PersonAddress getPersonAddressById(int personInfoId, int addressId);
    void updatePersonAddress(PersonAddress personAddress);
    void deletePersonAddress(int personInfoId, int addressId);
}
