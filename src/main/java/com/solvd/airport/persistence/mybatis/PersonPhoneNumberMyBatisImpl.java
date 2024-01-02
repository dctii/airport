package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PersonPhoneNumber;
import com.solvd.airport.persistence.PersonPhoneNumberDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PersonPhoneNumberMyBatisImpl implements PersonPhoneNumberDAO {

    @Override
    public void createPersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonPhoneNumberDAO dao = session.getMapper(PersonPhoneNumberDAO.class);
            dao.createPersonPhoneNumber(personPhoneNumberObj);
            session.commit();
        }
    }

    @Override
    public PersonPhoneNumber getPersonPhoneNumberById(int personInfoId, int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonPhoneNumberDAO dao = session.getMapper(PersonPhoneNumberDAO.class);
            return dao.getPersonPhoneNumberById(personInfoId, phoneNumberId);
        }
    }

    @Override
    public void updatePersonPhoneNumber(PersonPhoneNumber personPhoneNumberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonPhoneNumberDAO dao = session.getMapper(PersonPhoneNumberDAO.class);
            dao.updatePersonPhoneNumber(personPhoneNumberObj);
            session.commit();
        }
    }

    @Override
    public void deletePersonPhoneNumber(int personInfoId, int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonPhoneNumberDAO dao = session.getMapper(PersonPhoneNumberDAO.class);
            dao.deletePersonPhoneNumber(personInfoId, phoneNumberId);
            session.commit();
        }
    }
}
