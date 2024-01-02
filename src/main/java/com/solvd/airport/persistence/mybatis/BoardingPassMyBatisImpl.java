package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class BoardingPassMyBatisImpl implements BoardingPassDAO {

    @Override
    public void createBoardingPass(BoardingPass boardingPassObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(BoardingPassDAO.class);
            dao.createBoardingPass(boardingPassObj);
            session.commit();
        }
    }

    @Override
    public BoardingPass getBoardingPassById(int boardingPassId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(BoardingPassDAO.class);
            return dao.getBoardingPassById(boardingPassId);
        }
    }

    @Override
    public BoardingPass getBoardingPassByCheckInId(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(BoardingPassDAO.class);
            return dao.getBoardingPassByCheckInId(checkInId);
        }
    }

    @Override
    public void updateBoardingPass(BoardingPass boardingPassObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(BoardingPassDAO.class);
            dao.updateBoardingPass(boardingPassObj);
            session.commit();
        }
    }

    @Override
    public void deleteBoardingPass(int boardingPassId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(BoardingPassDAO.class);
            dao.deleteBoardingPass(boardingPassId);
            session.commit();
        }
    }
}
