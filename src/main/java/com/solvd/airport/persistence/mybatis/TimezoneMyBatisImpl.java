package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Timezone;
import com.solvd.airport.persistence.TimezoneDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimezoneMyBatisImpl implements TimezoneDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.TIMEZONE_MYBATIS_IMPL);

    private final Class<TimezoneDAO> MAPPER_DAO_CLASS = ClassConstants.TIMEZONE_DAO;


    @Override
    public int create(Timezone timezoneObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(timezoneObj);
            session.commit();

            generatedKey = timezoneObj.getTimezoneId();
        }

        return generatedKey;
    }

    @Override
    public Timezone getById(int timezoneId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(timezoneId);
        }
    }

    @Override
    public Timezone getTimezoneByTz(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getTimezoneByTz(timezone);
        }
    }

    @Override
    public void update(Timezone timezoneObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(timezoneObj);
            session.commit();
        }
    }

    @Override
    public void delete(int timezoneId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(timezoneId);
            session.commit();
        }
    }


    @Override
    public void deleteTimezoneByTz(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteTimezoneByTz(timezone);
            session.commit();
        }
    }

    @Override
    public boolean doesTimezoneExist(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesTimezoneExist(timezone);
        }
    }
}
