package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.persistence.AirportDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class AirportMyBatisImpl implements AirportDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRPORT_MYBATIS_IMPL);

    private final Class<AirportDAO> MAPPER_DAO_CLASS = ClassConstants.AIRPORT_DAO;

    @Override
    public int create(Airport airportObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(airportObj);
            session.commit();

            generatedKey = airportObj.getAirportId();
        }
        return generatedKey;
    }

    @Override
    public Airport getById(int airportId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(airportId);
        }
    }

    @Override
    public Airport getAirportByCode(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getAirportByCode(airportCode);
        }
    }

    @Override
    public void update(Airport airportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(airportObj);
            session.commit();
        }
    }

    @Override
    public void delete(int airportId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(airportId);
            session.commit();
        }
    }

    @Override
    public void deleteAirportByCode(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteAirportByCode(airportCode);
            session.commit();
        }
    }

    @Override
    public boolean doesAirportExist(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesAirportExist(airportCode);
        }
    }

    @Override
    public Set<Terminal> getTerminalsByAirportCode(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getTerminalsByAirportCode(airportCode);
        }
    }
}
