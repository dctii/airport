package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.PersonAddress;
import com.solvd.airport.persistence.PersonAddressDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class PersonAddressMyBatisImpl implements PersonAddressDAO {

    @Override
    public void createPersonAddress(PersonAddress personAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonAddressDAO dao = session.getMapper(PersonAddressDAO.class);
            dao.createPersonAddress(personAddressObj);
            session.commit();
        }
    }

    @Override
    public PersonAddress getPersonAddressById(int personInfoId, int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonAddressDAO dao = session.getMapper(PersonAddressDAO.class);
            return dao.getPersonAddressById(personInfoId, addressId);
        }
    }

    @Override
    public void updatePersonAddress(PersonAddress personAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonAddressDAO dao = session.getMapper(PersonAddressDAO.class);
            dao.updatePersonAddress(personAddressObj);
            session.commit();
        }
    }

    @Override
    public void deletePersonAddress(int personInfoId, int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            PersonAddressDAO dao = session.getMapper(PersonAddressDAO.class);
            dao.deletePersonAddress(personInfoId, addressId);
            session.commit();
        }
    }
}
