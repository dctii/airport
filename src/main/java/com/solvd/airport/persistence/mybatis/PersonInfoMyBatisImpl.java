package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class PersonInfoMyBatisImpl implements PersonInfoDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PERSON_INFO_MYBATIS_IMPL);

    private final Class<PersonInfoDAO> MAPPER_DAO_CLASS = ClassConstants.PERSON_INFO_DAO;

    @Override
    public int create(PersonInfo personInfoObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(personInfoObj);
            session.commit();

            generatedKey = personInfoObj.getPersonInfoId();
        }
        
        return generatedKey;
    }

    @Override
    public PersonInfo getById(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(personInfoId);
        }
    }

    @Override
    public PersonInfo getByPassportNumber(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getByPassportNumber(passportNumber);
        }
    }


    @Override
    public void update(PersonInfo personInfoObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(personInfoObj);
            session.commit();
        }
    }

    @Override
    public void delete(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(personInfoId);
            session.commit();
        }
    }

    @Override
    public Set<Address> getAddressesByPersonId(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getAddressesByPersonId(personInfoId);
        }
    }

    @Override
    public Set<PhoneNumber> getPhoneNumbersByPersonId(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPhoneNumbersByPersonId(personInfoId);
        }
    }

    @Override
    public Set<EmailAddress> getEmailAddressesByPersonId(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonInfoDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getEmailAddressesByPersonId(personInfoId);
        }
    }
}
