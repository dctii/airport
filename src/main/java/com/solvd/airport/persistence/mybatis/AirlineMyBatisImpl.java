package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Airline;
import com.solvd.airport.persistence.AirlineDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class AirlineMyBatisImpl implements AirlineDAO {

    @Override
    public void createAirline(Airline airlineObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO airportDAO = session.getMapper(AirlineDAO.class);
            airportDAO.createAirline(airlineObj);
            session.commit();
        }
    }

    @Override
    public void createAirlineWithoutAddress(Airline airlineObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO airportDAO = session.getMapper(AirlineDAO.class);
            airportDAO.createAirlineWithoutAddress(airlineObj);
            session.commit();
        }
    }

    @Override
    public boolean doesAirlineExist(String airlineCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineDAO airportDAO = session.getMapper(AirlineDAO.class);
            return airportDAO.doesAirlineExist(airlineCode);
        }
    }
}
