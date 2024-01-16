package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.PhoneNumberDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class PhoneNumberMyBatisImpl implements PhoneNumberDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PHONE_NUMBER_MYBATIS_IMPL);

    private final Class<PhoneNumberDAO> MAPPER_DAO_CLASS = ClassConstants.PHONE_NUMBER_DAO;


    @Override
    public int create(PhoneNumber phoneNumberObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(phoneNumberObj);
            session.commit();

            generatedKey = phoneNumberObj.getPhoneNumberId();
        }

        return generatedKey;
    }

    public void createPersonAssociation(int personInfoId, int contactId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.createPersonAssociation(personInfoId, contactId);
            session.commit();
        }
    }

    @Override
    public PhoneNumber getById(int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(phoneNumberId);
        }
    }

    @Override
    public PhoneNumber getPhoneNumberByNumber(String phoneNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPhoneNumberByNumber(phoneNumber);
        }
    }

    @Override
    public void update(PhoneNumber phoneNumberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(phoneNumberObj);
            session.commit();
        }
    }

    @Override
    public void delete(int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(phoneNumberId);
            session.commit();
        }
    }

    @Override
    public Set<PersonInfo> getPeopleByPhoneNumberId(int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPeopleByPhoneNumberId(phoneNumberId);
        }
    }
}
