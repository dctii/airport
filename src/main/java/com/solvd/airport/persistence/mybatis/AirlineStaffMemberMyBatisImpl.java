package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirlineStaffMemberMyBatisImpl implements AirlineStaffMemberDAO {
    private static final Logger LOGGER = LogManager.getLogger(AirlineStaffMemberMyBatisImpl.class);

    @Override
    public void createAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            dao.createAirlineStaffMember(staffMemberObj);
            session.commit();
        }
    }

    @Override
    public AirlineStaffMember getAirlineStaffMemberById(int airlineStaffId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            return dao.getAirlineStaffMemberById(airlineStaffId);
        }
    }

    @Override
    public AirlineStaffMember findByPersonInfoId(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            return dao.findByPersonInfoId(personInfoId);
        }
    }

    @Override
    public AirlineStaffMember findByEmailAddress(String emailAddress) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            return dao.findByEmailAddress(emailAddress);
        }
    }


    @Override
    public void updateAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            dao.updateAirlineStaffMember(staffMemberObj);
            session.commit();
        }
    }

    @Override
    public void deleteAirlineStaffMember(int airlineStaffId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(AirlineStaffMemberDAO.class);
            dao.deleteAirlineStaffMember(airlineStaffId);
            session.commit();
        }
    }
}
