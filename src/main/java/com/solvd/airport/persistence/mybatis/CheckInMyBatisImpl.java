package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckInMyBatisImpl implements CheckInDAO {

    private static final Logger LOGGER = LogManager.getLogger(CheckInMyBatisImpl.class);

    @Override
    public void createCheckIn(CheckIn checkInObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            dao.createCheckIn(checkInObj);
            session.commit();
        }
    }

    @Override
    public CheckIn getCheckInById(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            return dao.getCheckInById(checkInId);
        }
    }

    @Override
    public CheckIn getCheckInByBookingNumber(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            return dao.getCheckInByBookingNumber(bookingNumber);
        }
    }

    @Override
    public void updateCheckIn(CheckIn checkInObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            dao.updateCheckIn(checkInObj);
            session.commit();
        }
    }

    @Override
    public void deleteCheckIn(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            dao.deleteCheckIn(checkInId);
            session.commit();
        }
    }

    @Override
    public boolean hasCheckInForBookingId(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(CheckInDAO.class);
            return dao.hasCheckInForBookingId(bookingId);
        }
    }
}
