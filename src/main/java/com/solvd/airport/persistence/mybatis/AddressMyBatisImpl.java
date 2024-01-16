package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Address;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.AddressDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class AddressMyBatisImpl implements AddressDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.ADDRESS_MYBATIS_IMPL);

    private final Class<AddressDAO> MAPPER_DAO_CLASS = ClassConstants.ADDRESS_DAO;

    @Override
    public int create(Address addressObj) {
        int generatedKey = 0;
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(addressObj);
            session.commit();
            generatedKey = addressObj.getAddressId();
        }
        return generatedKey;
    }

    @Override
    public void createPersonAssociation(int personInfoId, int contactId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.createPersonAssociation(personInfoId, contactId);
            session.commit();
        }
    }

    @Override
    public Address getById(int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(addressId);
        }
    }

    @Override
    public Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getAddressByUniqueFields(street, city, postalCode, countryCode);
        }
    }

    @Override
    public void update(Address addressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(addressObj);
            session.commit();
        }
    }

    @Override
    public void delete(int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(addressId);
            session.commit();
        }
    }

    @Override
    public Set<PersonInfo> getPeopleByAddressId(int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPeopleByAddressId(addressId);
        }
    }
}
