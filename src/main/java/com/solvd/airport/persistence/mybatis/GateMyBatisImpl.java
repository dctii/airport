package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.Gate;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class GateMyBatisImpl implements GateDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.GATE_MYBATIS_IMPL);
    private final Class<GateDAO> MAPPER_DAO_CLASS = ClassConstants.GATE_DAO;


    @Override
    public int create(Gate gateObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(gateObj);
            session.commit();

            generatedKey = gateObj.getGateId();
        }
        return generatedKey;
    }

    @Override
    public Gate getById(int gateId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(gateId);
        }
    }

    @Override
    public Gate getGateByCodes(String gateCode, String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getGateByCodes(
                    gateCode,
                    airportCode,
                    terminalCode
            );
        }
    }

    @Override
    public void update(Gate gateObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(gateObj);
            session.commit();
        }
    }

    @Override
    public void delete(int gateId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(gateId);
            session.commit();
        }
    }

    @Override
    public void deleteByCodes(String gateCode, String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteByCodes(
                    gateCode,
                    airportCode,
                    terminalCode
            );
            session.commit();
        }
    }

    @Override
    public boolean doesGateExist(String gateCode, String airportCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesGateExist(gateCode, airportCode);
        }
    }

    @Override
    public Set<Flight> getFlightsByGateId(int gateId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getFlightsByGateId(gateId);
        }
    }
}
