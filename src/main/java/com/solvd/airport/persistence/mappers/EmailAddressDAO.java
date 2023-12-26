package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.EmailAddress;

public interface EmailAddressDAO {
    void createEmailAddress(EmailAddress emailAddress);
    EmailAddress getEmailAddressById(int id);
    EmailAddress getEmailAddressByEmail(String email);
    void updateEmailAddress(EmailAddress emailAddress);
    void deleteEmailAddress(int id);
}
