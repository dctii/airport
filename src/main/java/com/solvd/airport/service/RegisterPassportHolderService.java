package com.solvd.airport.service;

import com.solvd.airport.domain.*;

public interface RegisterPassportHolderService {
    void registerPassportHolder(
            PersonInfo personInfo,
            Passport passport,
            Address address,
            PhoneNumber phoneNumber,
            EmailAddress emailAddress
    );
}
