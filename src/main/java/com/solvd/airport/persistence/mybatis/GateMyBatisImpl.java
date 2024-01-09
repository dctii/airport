package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Gate;
import com.solvd.airport.persistence.GateDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class GateMyBatisImpl implements GateDAO {

    @Override
    public void createGate(Gate gateObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO gateDAO = session.getMapper(GateDAO.class);
            gateDAO.createGate(gateObj);
            session.commit();
        }
    }

    @Override
    public boolean doesGateExist(String airportCode, String terminalCode, String gateCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            GateDAO gateDAO = session.getMapper(GateDAO.class);
            return gateDAO.doesGateExist(airportCode, terminalCode, gateCode);
        }
    }
}
