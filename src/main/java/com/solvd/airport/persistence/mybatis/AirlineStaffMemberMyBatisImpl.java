package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirlineStaffMemberMyBatisImpl implements AirlineStaffMemberDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.AIRLINE_STAFF_MEMBER_MYBATIS_IMPL);

    private final Class<AirlineStaffMemberDAO> MAPPER_DAO_CLASS = ClassConstants.AIRLINE_STAFF_MEMBER_DAO;

    @Override
    public int create(AirlineStaffMember airlineStaffMemberObj) {
        int generatedKey = 0;
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(airlineStaffMemberObj);
            session.commit();

            generatedKey = airlineStaffMemberObj.getAirlineStaffId();
        }
        return generatedKey;
    }

    @Override
    public AirlineStaffMember getById(int airlineStaffId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(airlineStaffId);
        }
    }

    @Override
    public AirlineStaffMember getByPersonInfoId(int personInfoId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getByPersonInfoId(personInfoId);
        }
    }

    @Override
    public AirlineStaffMember getByEmailAddress(String emailAddress) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getByEmailAddress(emailAddress);
        }
    }


    @Override
    public void update(AirlineStaffMember airlineStaffMemberObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(airlineStaffMemberObj);
            session.commit();
        }
    }

    @Override
    public void delete(int airlineStaffId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AirlineStaffMemberDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(airlineStaffId);
            session.commit();
        }
    }
}
