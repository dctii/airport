package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Country;
import com.solvd.airport.persistence.CountryDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class CountryMyBatisImpl implements CountryDAO {

    @Override
    public void createCountry(Country countryObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(CountryDAO.class);
            dao.createCountry(countryObj);
            session.commit();
        }
    }

    @Override
    public Country getCountryByCode(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(CountryDAO.class);
            return dao.getCountryByCode(countryCode);
        }
    }

    @Override
    public void updateCountry(Country countryObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(CountryDAO.class);
            dao.updateCountry(countryObj);
            session.commit();
        }
    }

    @Override
    public void deleteCountry(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(CountryDAO.class);
            dao.deleteCountry(countryCode);
            session.commit();
        }
    }

    @Override
    public boolean doesCountryCodeExist(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(CountryDAO.class);
            return dao.doesCountryCodeExist(countryCode);
        }
    }
}
