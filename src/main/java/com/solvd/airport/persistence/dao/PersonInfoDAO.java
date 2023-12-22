package com.solvd.airport.persistence.dao;

import com.solvd.airport.domain.PersonInfo;

public interface PersonInfoDAO {
    PersonInfo findById(int id);

    PersonInfo findByName(String surname, String givenName);

}
