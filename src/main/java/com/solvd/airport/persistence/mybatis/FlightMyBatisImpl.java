package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.FlightStaffMember;
import com.solvd.airport.persistence.FlightDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class FlightMyBatisImpl implements FlightDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.FLIGHT_MYBATIS_IMPL);

    private final Class<FlightDAO> MAPPER_DAO_CLASS = ClassConstants.FLIGHT_DAO;

    @Override
    public int create(Flight flightObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(flightObj);
            session.commit();

            generatedKey = flightObj.getFlightId();
        }
        return generatedKey;
    }

    @Override
    public Flight getById(int flightId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(flightId);
        }
    }

    @Override
    public Flight getFlightByCode(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getFlightByCode(flightCode);
        }
    }

    @Override
    public void update(Flight flightObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(flightObj);
            session.commit();
        }
    }

    @Override
    public void updateFlightByCode(Flight flightObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updateFlightByCode(flightObj);
            session.commit();
        }
    }

    @Override
    public void delete(int flightId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(flightId);
            session.commit();
        }
    }

    @Override
    public void deleteFlightByCode(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteFlightByCode(flightCode);
            session.commit();
        }
    }

    @Override
    public boolean doesFlightExist(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesFlightExist(flightCode);
        }
    }

    @Override
    public Set<FlightStaffMember> getFlightCrewByFlightCode(String flightCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            FlightDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getFlightCrewByFlightCode(flightCode);
        }
    }
}
