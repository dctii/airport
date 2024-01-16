package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.domain.Passport;
import com.solvd.airport.persistence.PassportDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class PassportMyBatisImpl implements PassportDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.PASSPORT_MYBATIS_IMPL);

    private final Class<PassportDAO> MAPPER_DAO_CLASS = ClassConstants.PASSPORT_DAO;

    @Override
    public int create(Passport passportObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(passportObj);
            session.commit();

            generatedKey = passportObj.getPassportId();
        }
        return generatedKey;
    }

    @Override
    public Passport getById(int passportId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(passportId);
        }
    }

    @Override
    public Passport getPassportByPassportNumber(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPassportByPassportNumber(passportNumber);
        }
    }

    @Override
    public void update(Passport passportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(passportObj);
            session.commit();
        }
    }

    @Override
    public void updatePassportByPassportNumber(Passport passportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updatePassportByPassportNumber(passportObj);
            session.commit();
        }
    }

    @Override
    public void delete(int passportId) {

    }

    @Override
    public void deletePassportByPassportNumber(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deletePassportByPassportNumber(passportNumber);
            session.commit();
        }
    }

    @Override
    public boolean doesPassportExist(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesPassportExist(passportNumber);
        }
    }

    @Override
    public Set<Booking> getBookingsByPassportNumber(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getBookingsByPassportNumber(passportNumber);
        }
    }
}
