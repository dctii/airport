package com.solvd.airport.persistence;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDAO {
    void createEmailAddress(@Param("emailAddressObj") EmailAddress emailAddressObj);

    EmailAddress getEmailAddressById(@Param("emailAddressId") int emailAddressId);

    EmailAddress getEmailAddressByEmail(@Param("email") String email);

    void updateEmailAddress(@Param("emailAddressObj") EmailAddress emailAddressObj);

    void deleteEmailAddress(@Param("emailAddressId") int emailAddressId);

    String TABLE_NAME = "email_addresses";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_EMAIL_ADDRESS_ID = "email_address_id";
    String COL_EMAIL_ADDRESS = "email_address";
    String EXPLICIT_COL_EMAIL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EMAIL_ADDRESS_ID);
    String EXPLICIT_COL_EMAIL_ADDRESS = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EMAIL_ADDRESS);
}

