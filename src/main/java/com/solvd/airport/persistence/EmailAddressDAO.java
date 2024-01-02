package com.solvd.airport.persistence;

import com.solvd.airport.domain.EmailAddress;
import org.apache.ibatis.annotations.Param;

public interface EmailAddressDAO {
    void createEmailAddress(@Param("emailAddressObj") EmailAddress emailAddressObj);
    EmailAddress getEmailAddressById(@Param("emailAddressId") int emailAddressId);
    EmailAddress getEmailAddressByEmail(@Param("email") String email);
    void updateEmailAddress(@Param("emailAddressObj") EmailAddress emailAddressObj);
    void deleteEmailAddress(@Param("emailAddressId") int emailAddressId);
}
