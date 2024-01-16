package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.util.ClassConstants;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class EmailAddressMyBatisImpl implements EmailAddressDAO {
    private static final Logger LOGGER = LogManager.getLogger(ClassConstants.EMAIL_ADDRESS_MYBATIS_IMPL);

    private final Class<EmailAddressDAO> MAPPER_DAO_CLASS = ClassConstants.EMAIL_ADDRESS_DAO;


    @Override
    public int create(EmailAddress emailAddressObj) {
        int generatedKey = 0;

        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.create(emailAddressObj);
            session.commit();

            generatedKey = emailAddressObj.getEmailAddressId();
        }
        return generatedKey;
    }

    @Override
    public void createPersonAssociation(int personInfoId, int contactId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.createPersonAssociation(personInfoId, contactId);
            session.commit();
        }
    }

    @Override
    public EmailAddress getById(int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getById(emailAddressId);
        }
    }

    @Override
    public EmailAddress getEmailAddressByEmail(String email) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getEmailAddressByEmail(email);
        }
    }

    @Override
    public void update(EmailAddress emailAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.update(emailAddressObj);
            session.commit();
        }
    }

    @Override
    public void delete(int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            dao.delete(emailAddressId);
            session.commit();
        }
    }

    @Override
    public Set<PersonInfo> getPeopleByEmailAddressId(int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(MAPPER_DAO_CLASS);
            return dao.getPeopleByEmailAddressId(emailAddressId);
        }
    }
}
