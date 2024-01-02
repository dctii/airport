package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Passport;
import com.solvd.airport.persistence.PassportDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PassportMyBatisImpl implements PassportDAO {

    @Override
    public void createPassport(Passport passportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(PassportDAO.class);
            dao.createPassport(passportObj);
            session.commit();
        }
    }

    @Override
    public Passport getPassportById(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(PassportDAO.class);
            return dao.getPassportById(passportNumber);
        }
    }

    @Override
    public void updatePassport(Passport passportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(PassportDAO.class);
            dao.updatePassport(passportObj);
            session.commit();
        }
    }

    @Override
    public void deletePassport(String passportNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PassportDAO dao = session.getMapper(PassportDAO.class);
            dao.deletePassport(passportNumber);
            session.commit();
        }
    }
}
