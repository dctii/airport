package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.Passport;

public interface PassportDAO {
    void createPassport(Passport passport);

    Passport getPassportById(String passportNumber);

    void updatePassport(Passport passport);

    void deletePassport(String passportNumber);
}
