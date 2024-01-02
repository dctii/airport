package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Address;
import com.solvd.airport.persistence.AddressDAO;
import org.apache.ibatis.session.SqlSession;
import com.solvd.airport.util.MyBatisUtils;

public class AddressMyBatisImpl implements AddressDAO {

    @Override
    public void createAddress(Address addressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO addressDAO = session.getMapper(AddressDAO.class);
            addressDAO.createAddress(addressObj);
            session.commit();
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO addressDAO = session.getMapper(AddressDAO.class);
            return addressDAO.getAddressById(addressId);
        }
    }

    @Override
    public Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO addressDAO = session.getMapper(AddressDAO.class);
            return addressDAO.getAddressByUniqueFields(street, city, postalCode, countryCode);
        }
    }

    @Override
    public void updateAddress(Address addressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO addressDAO = session.getMapper(AddressDAO.class);
            addressDAO.updateAddress(addressObj);
            session.commit();
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            AddressDAO addressDAO = session.getMapper(AddressDAO.class);
            addressDAO.deleteAddress(addressId);
            session.commit();
        }
    }
}
