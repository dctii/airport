package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.persistence.AirportDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class AirportMyBatisImpl implements AirportDAO {

    @Override
    public void createAirport(Airport airportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            airportDAO.createAirport(airportObj);
            session.commit();
        }
    }

    @Override
    public void createAirportWithoutAddress(Airport airportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            airportDAO.createAirportWithoutAddress(airportObj);
            session.commit();
        }
    }

    @Override
    public Airport getAirportByCode(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            return airportDAO.getAirportByCode(airportCode);
        }
    }

    @Override
    public void updateAirport(Airport airportObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            airportDAO.updateAirport(airportObj);
            session.commit();
        }
    }

    @Override
    public void deleteAirport(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            airportDAO.deleteAirport(airportCode);
            session.commit();
        }
    }

    @Override
    public boolean doesAirportExist(String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirportDAO airportDAO = session.getMapper(AirportDAO.class);
            return airportDAO.doesAirportExist(airportCode);
        }
    }
}
