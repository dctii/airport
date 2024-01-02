package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class FlightMyBatisImpl implements FlightDAO {

    @Override
    public void createFlight(Flight flightObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(FlightDAO.class);
            dao.createFlight(flightObj);
            session.commit();
        }
    }

    @Override
    public Flight getFlightByCode(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(FlightDAO.class);
            return dao.getFlightByCode(flightCode);
        }
    }

    @Override
    public void updateFlight(Flight flightObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(FlightDAO.class);
            dao.updateFlight(flightObj);
            session.commit();
        }
    }

    @Override
    public void deleteFlight(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(FlightDAO.class);
            dao.deleteFlight(flightCode);
            session.commit();
        }
    }
}
