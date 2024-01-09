package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Terminal;
import com.solvd.airport.persistence.TerminalDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class TerminalMyBatisImpl implements TerminalDAO {

    @Override
    public void createTerminal(Terminal terminalObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO terminalDAO = session.getMapper(TerminalDAO.class);
            terminalDAO.createTerminal(terminalObj);
            session.commit();
        }
    }

    @Override
    public boolean doesTerminalExist(String airportCode, String terminalCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            TerminalDAO terminalDAO = session.getMapper(TerminalDAO.class);
            return terminalDAO.doesTerminalExist(airportCode, terminalCode);
        }
    }
}
