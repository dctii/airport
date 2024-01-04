package com.solvd.airport.persistence;

import com.solvd.airport.domain.PersonEmailAddress;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface PersonEmailAddressDAO {
    void createPersonEmailAddress(@Param("personEmailAddressObj") PersonEmailAddress personEmailAddressObj);
    PersonEmailAddress getPersonEmailAddressById(
            @Param("personInfoId") int personInfoId,
            @Param("emailAddressId") int emailAddressId
    );
    PersonEmailAddress getPersonEmailAddressByEmail(@Param("emailAddress") String emailAddress);
    void updatePersonEmailAddress(@Param("personEmailAddressObj") PersonEmailAddress personEmailAddressObj);
    void deletePersonEmailAddress(
            @Param("personInfoId") int personInfoId,
            @Param("emailAddressId") int emailAddressId
    );

    String TABLE_NAME = "person_email_addresses";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String COL_EMAIL_ADDRESS_ID = EmailAddressDAO.COL_EMAIL_ADDRESS_ID;
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
    String EXPLICIT_COL_EMAIL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EMAIL_ADDRESS_ID);

}
