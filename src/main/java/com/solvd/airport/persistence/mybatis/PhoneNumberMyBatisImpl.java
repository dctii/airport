package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PhoneNumber;
import com.solvd.airport.persistence.PhoneNumberDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PhoneNumberMyBatisImpl implements PhoneNumberDAO {

    @Override
    public void createPhoneNumber(PhoneNumber phoneNumberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(PhoneNumberDAO.class);
            dao.createPhoneNumber(phoneNumberObj);
            session.commit();
        }
    }

    @Override
    public PhoneNumber getPhoneNumberById(int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(PhoneNumberDAO.class);
            return dao.getPhoneNumberById(phoneNumberId);
        }
    }

    @Override
    public PhoneNumber getPhoneNumberByNumber(String phoneNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(PhoneNumberDAO.class);
            return dao.getPhoneNumberByNumber(phoneNumber);
        }
    }

    @Override
    public void updatePhoneNumber(PhoneNumber phoneNumberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(PhoneNumberDAO.class);
            dao.updatePhoneNumber(phoneNumberObj);
            session.commit();
        }
    }

    @Override
    public void deletePhoneNumber(int phoneNumberId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PhoneNumberDAO dao = session.getMapper(PhoneNumberDAO.class);
            dao.deletePhoneNumber(phoneNumberId);
            session.commit();
        }
    }
}
