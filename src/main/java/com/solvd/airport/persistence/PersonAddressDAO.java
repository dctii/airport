package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonAddress;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PersonAddressDAO {
    void createPersonAddress(@Param("personAddressObj") PersonAddress personAddressObj);

    PersonAddress getPersonAddressById(@Param("personInfoId") int personInfoId, @Param("addressId") int addressId);

    void updatePersonAddress(@Param("personAddressObj") PersonAddress personAddressObj);

    void deletePersonAddress(
            @Param("personInfoId") int personInfoId,
            @Param("addressId") int addressId
    );

    String TABLE_NAME = "person_addresses";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String COL_ADDRESS_ID = AddressDAO.COL_ADDRESS_ID;
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
    String EXPLICIT_COL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);
}
