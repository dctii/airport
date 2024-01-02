package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Booking;
import com.solvd.airport.persistence.BookingDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingMyBatisImpl implements BookingDAO {
    private static final Logger LOGGER = LogManager.getLogger(BookingMyBatisImpl.class);

    @Override
    public void createBooking(Booking bookingObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(BookingDAO.class);
            dao.createBooking(bookingObj);
            session.commit();
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(BookingDAO.class);
            return dao.getBookingById(bookingId);
        }
    }

    @Override
    public Booking findByBookingNumber(String bookingNumber) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(BookingDAO.class);

            return dao.findByBookingNumber(bookingNumber);
        }
    }

    @Override
    public void updateBooking(Booking bookingObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(BookingDAO.class);
            dao.updateBooking(bookingObj);
            session.commit();
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BookingDAO dao = session.getMapper(BookingDAO.class);
            dao.deleteBooking(bookingId);
            session.commit();
        }
    }
}
