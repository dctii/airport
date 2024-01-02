package com.solvd.airport.persistence;

import com.solvd.airport.domain.PhoneNumber;
import org.apache.ibatis.annotations.Param;

public interface PhoneNumberDAO {
    void createPhoneNumber(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);
    PhoneNumber getPhoneNumberById(@Param("phoneNumberId") int phoneNumberId);
    PhoneNumber getPhoneNumberByNumber(@Param("phoneNumber") String phoneNumber);
    void updatePhoneNumber(@Param("phoneNumberObj") PhoneNumber phoneNumberObj);
    void deletePhoneNumber(@Param("phoneNumberId") int phoneNumberId);
}
