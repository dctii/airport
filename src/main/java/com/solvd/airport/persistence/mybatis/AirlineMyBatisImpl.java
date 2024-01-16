package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Airline;
import com.solvd.airport.persistence.AirlineDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirlineMyBatisImpl implements AirlineDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRLINE_MYBATIS_IMPL);

    private final Class<AirlineDAO> MAPPER_DAO_CLASS = ClassConstants.AIRLINE_DAO;

    @Override
    public int create(Airline airlineObj) {
        int generatedKey = 0;
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(airlineObj);
            session.commit();

            generatedKey = airlineObj.getAirlineId();
        }
        return generatedKey;
    }

    @Override
    public Airline getById(int airlineId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(airlineId);
        }
    }

    @Override
    public Airline getAirlineByCode(String airlineCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getAirlineByCode(airlineCode);
        }
    }

    @Override
    public void update(Airline airlineObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(airlineObj);
            session.commit();
        }
    }

    @Override
    public void updateAirlineByCode(Airline airlineObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updateAirlineByCode(airlineObj);
            session.commit();
        }
    }

    @Override
    public void delete(int airlineId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(airlineId);
            session.commit();
        }
    }

    @Override
    public void deleteAirlineByCode(String airlineCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteAirlineByCode(airlineCode);
            session.commit();
        }
    }

    @Override
    public boolean doesAirlineExist(String airlineCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesAirlineExist(airlineCode);
        }
    }
}
