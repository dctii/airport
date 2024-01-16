package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.Country;
import com.solvd.airport.persistence.CountryDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountryMyBatisImpl implements CountryDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.COUNTRY_MYBATIS_IMPL);

    private final Class<CountryDAO> MAPPER_DAO_CLASS = ClassConstants.COUNTRY_DAO;

    @Override
    public int create(Country countryObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(countryObj);
            session.commit();

            generatedKey = countryObj.getCountryId();
        }
        return generatedKey;
    }

    @Override
    public Country getById(int countryId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(countryId);
        }
    }

    @Override
    public Country getCountryByCode(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getCountryByCode(countryCode);
        }
    }

    @Override
    public void update(Country countryObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(countryObj);
            session.commit();
        }
    }

    @Override
    public void updateCountryByCode(Country countryObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.updateCountryByCode(countryObj);
            session.commit();
        }
    }

    @Override
    public void delete(int countryId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(countryId);
            session.commit();
        }
    }

    @Override
    public void deleteCountryByCode(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.deleteCountryByCode(countryCode);
            session.commit();
        }
    }

    @Override
    public boolean doesCountryCodeExist(String countryCode) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            CountryDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.doesCountryCodeExist(countryCode);
        }
    }
}
