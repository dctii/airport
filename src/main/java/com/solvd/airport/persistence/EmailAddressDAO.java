package com.solvd.airport.persistence;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface EmailAddressDAO extends ContactDAO<EmailAddress> {
    int create(@Param("emailAddressObj") EmailAddress emailAddressObj);

    EmailAddress getById(@Param("emailAddressId") int emailAddressId);

    EmailAddress getEmailAddressByEmail(@Param("email") String email);

    void update(@Param("emailAddressObj") EmailAddress emailAddressObj);

    void delete(@Param("emailAddressId") int emailAddressId);

    Set<PersonInfo> getPeopleByEmailAddressId(@Param("emailAddressId") int emailAddressId);

    String TABLE_NAME = "email_addresses";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_EMAIL_ADDRESS_ID = "email_address_id";
    String COL_EMAIL_ADDRESS = "email_address";
    String EXPLICIT_COL_EMAIL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EMAIL_ADDRESS_ID);
    String EXPLICIT_COL_EMAIL_ADDRESS = SQLUtils.qualifyColumnName(TABLE_NAME, COL_EMAIL_ADDRESS);

    String PERSON_EMAIL_ADDRESSES_TABLE_NAME = PersonInfoDAO.PERSON_EMAIL_ADDRESSES_TABLE_NAME;
}

