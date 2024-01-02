package com.solvd.airport.persistence;

import com.solvd.airport.domain.Passport;
import org.apache.ibatis.annotations.Param;

public interface PassportDAO {
    void createPassport(@Param("passportObj") Passport passportObj);

    Passport getPassportById(@Param("passportNumber") String passportNumber);

    void updatePassport(@Param("passportObj") Passport passportObj);

    void deletePassport(@Param("passportNumber") String passportNumber);

// TODO:   boolean doesPassportExist(@Param("passportNumber") String passportNumber);
}
