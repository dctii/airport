package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.PersonInfo;

public interface PersonInfoDAO {

    PersonInfo findByName(String surname, String givenName);
    void createPersonInfo(PersonInfo personInfo);
    PersonInfo getPersonInfoById(int id);
    void updatePersonInfo(PersonInfo personInfo);
    void deletePersonInfo(int id);

}
