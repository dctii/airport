package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Timezone;
import com.solvd.airport.persistence.TimezoneDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class TimezoneMyBatisImpl implements TimezoneDAO {
    @Override
    public void createTimezone(Timezone timezoneObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(TimezoneDAO.class);
            dao.createTimezone(timezoneObj);
            session.commit();
        }
    }

    @Override
    public Timezone getTimezoneByTz(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(TimezoneDAO.class);
            return dao.getTimezoneByTz(timezone);
        }
    }

    @Override
    public void updateTimezoneByTz(String oldTimezone, Timezone newTimezoneObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(TimezoneDAO.class);
            dao.updateTimezoneByTz(oldTimezone, newTimezoneObj);
            session.commit();
        }
    }

    @Override
    public void deleteTimezone(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(TimezoneDAO.class);
            dao.deleteTimezone(timezone);
            session.commit();
        }
    }

    @Override
    public boolean doesTimezoneExist(String timezone) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TimezoneDAO dao = session.getMapper(TimezoneDAO.class);
            return dao.doesTimezoneExist(timezone);
        }
    }
}
