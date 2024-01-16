package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.CheckIn;
import com.solvd.airport.persistence.CheckInDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckInMyBatisImpl implements CheckInDAO {

    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.CHECK_IN_MYBATIS_IMPL);

    private final Class<CheckInDAO> MAPPER_DAO_CLASS = ClassConstants.CHECK_IN_DAO;



    @Override
    public int create(CheckIn checkInObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(checkInObj);
            session.commit();

            generatedKey = checkInObj.getCheckInId();
        }
        return generatedKey;
    }

    @Override
    public CheckIn getById(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(checkInId);
        }
    }

    @Override
    public CheckIn getCheckInByBookingNumber(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getCheckInByBookingNumber(bookingNumber);
        }
    }

    @Override
    public void update(CheckIn checkInObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(checkInObj);
            session.commit();
        }
    }

    @Override
    public void delete(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(checkInId);
            session.commit();
        }
    }

    @Override
    public boolean hasCheckInForBookingId(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CheckInDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.hasCheckInForBookingId(bookingId);
        }
    }
}
