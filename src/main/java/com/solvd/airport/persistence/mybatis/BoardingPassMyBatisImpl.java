package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.BoardingPass;
import com.solvd.airport.persistence.BoardingPassDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardingPassMyBatisImpl implements BoardingPassDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BOARDING_PASS_MYBATIS_IMPL);

    private final Class<BoardingPassDAO> MAPPER_DAO_CLASS = ClassConstants.BOARDING_PASS_DAO;

    @Override
    public int create(BoardingPass boardingPassObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(boardingPassObj);
            session.commit();

            generatedKey = boardingPassObj.getBoardingPassId();
        }
        return generatedKey;
    }

    @Override
    public BoardingPass getById(int boardingPassId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(boardingPassId);
        }
    }

    @Override
    public BoardingPass getBoardingPassByCheckInId(int checkInId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getBoardingPassByCheckInId(checkInId);
        }
    }

    @Override
    public void update(BoardingPass boardingPassObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(boardingPassObj);
            session.commit();
        }
    }

    @Override
    public void delete(int boardingPassId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BoardingPassDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(boardingPassId);
            session.commit();
        }
    }
}
