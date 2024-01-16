package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.domain.Terminal;
import com.solvd.airport.persistence.TerminalDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class TerminalMyBatisImpl implements TerminalDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.TERMINAL_MYBATIS_IMPL);

    private final Class<TerminalDAO> MAPPER_DAO_CLASS = ClassConstants.TERMINAL_DAO;

    @Override
    public int create(Terminal terminalObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(terminalObj);
            session.commit();

            generatedKey = terminalObj.getTerminalId();
        }

        return generatedKey;
    }

    @Override
    public Terminal getById(int terminalId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(terminalId);
        }
    }

    @Override
    public Terminal getTerminalByCodes(String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getTerminalByCodes(airportCode, terminalCode);
        }
    }

    @Override
    public void update(Terminal terminalObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(terminalObj);
            session.commit();
        }
    }

    @Override
    public void delete(int terminalId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(terminalId);
            session.commit();
        }
    }

    @Override
    public void deleteTerminalByCodes(String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteTerminalByCodes(airportCode, terminalCode);
            session.commit();
        }
    }

    @Override
    public boolean doesTerminalExist(String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesTerminalExist(airportCode, terminalCode);
        }
    }

    @Override
    public Set<Gate> getGatesByCodes(String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getGatesByCodes(airportCode, terminalCode);
        }
    }
}
