package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PersonEmailAddress;
import com.solvd.airport.persistence.PersonEmailAddressDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PersonEmailAddressMyBatisImpl implements PersonEmailAddressDAO {

    @Override
    public void createPersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonEmailAddressDAO dao = session.getMapper(PersonEmailAddressDAO.class);
            dao.createPersonEmailAddress(personEmailAddressObj);
            session.commit();
        }
    }

    @Override
    public PersonEmailAddress getPersonEmailAddressById(int personInfoId, int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonEmailAddressDAO dao = session.getMapper(PersonEmailAddressDAO.class);
            return dao.getPersonEmailAddressById(personInfoId, emailAddressId);
        }
    }

    @Override
    public PersonEmailAddress getPersonEmailAddressByEmail(String emailAddress) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonEmailAddressDAO dao = session.getMapper(PersonEmailAddressDAO.class);
            return dao.getPersonEmailAddressByEmail(emailAddress);
        }
    }

    @Override
    public void updatePersonEmailAddress(PersonEmailAddress personEmailAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonEmailAddressDAO dao = session.getMapper(PersonEmailAddressDAO.class);
            dao.updatePersonEmailAddress(personEmailAddressObj);
            session.commit();
        }
    }

    @Override
    public void deletePersonEmailAddress(int personInfoId, int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonEmailAddressDAO dao = session.getMapper(PersonEmailAddressDAO.class);
            dao.deletePersonEmailAddress(personInfoId, emailAddressId);
            session.commit();
        }
    }
}
