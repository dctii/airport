package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonAddress;
import org.apache.ibatis.annotations.Param;

public interface PersonAddressDAO {
    void createPersonAddress(@Param("personAddressObj") PersonAddress personAddressObj);
    PersonAddress getPersonAddressById(@Param("personInfoId") int personInfoId, @Param("addressId") int addressId);
    void updatePersonAddress(@Param("personAddressObj") PersonAddress personAddressObj);
    void deletePersonAddress(@Param("personInfoId") int personInfoId, @Param("addressId") int addressId);
}
