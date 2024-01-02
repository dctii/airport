package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PersonInfoMyBatisImpl implements PersonInfoDAO {

    @Override
    public void createPersonInfo(PersonInfo personInfoObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(PersonInfoDAO.class);
            dao.createPersonInfo(personInfoObj);
            session.commit();
        }
    }

    @Override
    public PersonInfo getPersonInfoById(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(PersonInfoDAO.class);
            return dao.getPersonInfoById(personInfoId);
        }
    }

    @Override
    public PersonInfo findByName(String surname, String givenName) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(PersonInfoDAO.class);
            return dao.findByName(surname, givenName);
        }
    }

    @Override
    public void updatePersonInfo(PersonInfo personInfoObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(PersonInfoDAO.class);
            dao.updatePersonInfo(personInfoObj);
            session.commit();
        }
    }

    @Override
    public void deletePersonInfo(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(PersonInfoDAO.class);
            dao.deletePersonInfo(personInfoId);
            session.commit();
        }
    }
}
