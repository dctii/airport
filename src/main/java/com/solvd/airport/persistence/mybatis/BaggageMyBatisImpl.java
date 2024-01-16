package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Baggage;
import com.solvd.airport.persistence.BaggageDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaggageMyBatisImpl implements BaggageDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.BAGGAGE_MYBATIS_IMPL);

    private final Class<BaggageDAO> MAPPER_DAO_CLASS = ClassConstants.BAGGAGE_DAO;

    @Override
    public int create(Baggage baggageObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(baggageObj);
            session.commit();

            generatedKey = baggageObj.getBaggageId();
        }
        return generatedKey;
    }

    @Override
    public Baggage getById(int baggageId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(baggageId);
        }
    }

    @Override
    public Baggage getBaggageByCode(String baggageCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getBaggageByCode(baggageCode);
        }
    }

    @Override
    public void update(Baggage baggageObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(baggageObj);
            session.commit();
        }
    }

    @Override
    public void updateBaggageByCode(Baggage baggageObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updateBaggageByCode(baggageObj);
            session.commit();
        }
    }

    @Override
    public void delete(int baggageId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(baggageId);
            session.commit();
        }
    }

    @Override
    public void deleteBaggageByCode(String baggageCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            BaggageDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteBaggageByCode(baggageCode);
            session.commit();
        }
    }

}
