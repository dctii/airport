package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.PhoneNumber;

public interface PhoneNumberDAO {
    void createPhoneNumber(PhoneNumber phoneNumber);
    PhoneNumber getPhoneNumberById(int id);
    PhoneNumber getPhoneNumberByNumber(String phoneNumber);
    void updatePhoneNumber(PhoneNumber phoneNumber);
    void deletePhoneNumber(int id);
}
