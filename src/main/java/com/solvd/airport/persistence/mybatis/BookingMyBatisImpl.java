package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingMyBatisImpl implements BookingDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOOKING_MYBATIS_IMPL);

    private final Class<BookingDAO> MAPPER_DAO_CLASS = ClassConstants.BOOKING_DAO;

    @Override
    public int create(Booking bookingObj) {
        int generatedKey = 0;
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(bookingObj);
            session.commit();

            generatedKey = bookingObj.getBookingId();
        }
        return generatedKey;
    }

    @Override
    public Booking getById(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(bookingId);
        }
    }

    @Override
    public Booking getBookingByBookingNumber(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);

            return dao.getBookingByBookingNumber(bookingNumber);
        }
    }

    @Override
    public void update(Booking bookingObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(bookingObj);
            session.commit();
        }
    }

    @Override
    public void updateBookingByBookingNumber(Booking bookingObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updateBookingByBookingNumber(bookingObj);
            session.commit();
        }
    }

    @Override
    public void delete(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(bookingId);
            session.commit();
        }
    }

    @Override
    public void deleteBookingByBookingNumber(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteBookingByBookingNumber(bookingNumber);
            session.commit();
        }
    }

    @Override
    public boolean doesBookingExist(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesBookingExist(bookingNumber);
        }
    }
}
