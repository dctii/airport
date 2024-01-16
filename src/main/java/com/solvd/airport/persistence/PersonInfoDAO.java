package com.solvd.airport.persistence;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PersonInfoDAO extends AbstractDAO<PersonInfo> {

    int create(@Param("personInfoObj") PersonInfo personInfoObj);

    PersonInfo getById(@Param("personInfoId") int personInfoId);

    PersonInfo getByPassportNumber(@Param("passportNumber") String passportNumber);

    void update(@Param("personInfoObj") PersonInfo personInfoObj);

    void delete(@Param("personInfoId") int personInfoId);

    Set<Address> getAddressesByPersonId(@Param("personInfoId") int personInfoId);

    Set<PhoneNumber> getPhoneNumbersByPersonId(@Param("personInfoId") int personInfoId);

    Set<EmailAddress> getEmailAddressesByPersonId(@Param("personInfoId") int personInfoId);

    String TABLE_NAME = "person_info";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_PERSON_INFO_ID = "person_info_id";
    String COL_SURNAME = "surname";
    String COL_GIVEN_NAME = "given_name";
    String COL_MIDDLE_NAME = "middle_name";
    String COL_BIRTHDATE = "birthdate";
    String COL_SEX = "sex";
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
    String EXPLICIT_COL_SURNAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SURNAME);
    String EXPLICIT_COL_GIVEN_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_GIVEN_NAME);
    String EXPLICIT_COL_MIDDLE_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_MIDDLE_NAME);
    String EXPLICIT_COL_BIRTHDATE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_BIRTHDATE);
    String EXPLICIT_COL_SEX = SQLUtils.qualifyColumnName(TABLE_NAME, COL_SEX);

    String PERSON_ADDRESSES_TABLE_NAME = "person_addresses";
    String PA_EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(PERSON_ADDRESSES_TABLE_NAME, COL_PERSON_INFO_ID);
    String PA_EXPLICIT_COL_ADDRESS_ID = SQLUtils.qualifyColumnName(PERSON_ADDRESSES_TABLE_NAME, AddressDAO.COL_ADDRESS_ID);
    String PERSON_PHONE_NUMBER_TABLE_NAME = "person_phone_numbers";
    String PPN_EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(PERSON_PHONE_NUMBER_TABLE_NAME, COL_PERSON_INFO_ID);
    String PPN_EXPLICIT_COL_PHONE_NUMBER_ID = SQLUtils.qualifyColumnName(PERSON_PHONE_NUMBER_TABLE_NAME, PhoneNumberDAO.COL_PHONE_NUMBER_ID);
    String PERSON_EMAIL_ADDRESSES_TABLE_NAME = "person_email_addresses";
    String PEA_EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(PERSON_EMAIL_ADDRESSES_TABLE_NAME, COL_PERSON_INFO_ID);
    String PEA_EXPLICIT_COL_EMAIL_ADDRESS_ID = SQLUtils.qualifyColumnName(PERSON_EMAIL_ADDRESSES_TABLE_NAME, EmailAddressDAO.COL_EMAIL_ADDRESS_ID);

}
