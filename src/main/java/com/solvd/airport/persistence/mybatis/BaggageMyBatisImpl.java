package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.persistence.BaggageDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class BaggageMyBatisImpl implements BaggageDAO {

    @Override
    public void createBaggage(Baggage baggageObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(BaggageDAO.class);
            dao.createBaggage(baggageObj);
            session.commit();
        }
    }

    @Override
    public Baggage getBaggageByCode(String baggageCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(BaggageDAO.class);
            return dao.getBaggageByCode(baggageCode);
        }
    }

    @Override
    public void updateBaggage(Baggage baggageObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(BaggageDAO.class);
            dao.updateBaggage(baggageObj);
            session.commit();
        }
    }

    @Override
    public void deleteBaggage(String baggageCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(BaggageDAO.class);
            dao.deleteBaggage(baggageCode);
            session.commit();
        }
    }

    @Override
    public void updateBaggageWithBoardingPassId(String baggageCode, int boardingPassId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(BaggageDAO.class);
            dao.updateBaggageWithBoardingPassId(baggageCode, boardingPassId);
            session.commit();
        }
    }
}
