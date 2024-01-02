package com.solvd.airport.persistence.mybatis;

import com.solvd.airport.domain.EmailAddress;
import com.solvd.airport.persistence.EmailAddressDAO;
import com.solvd.airport.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class EmailAddressMyBatisImpl implements EmailAddressDAO {

    @Override
    public void createEmailAddress(EmailAddress emailAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(EmailAddressDAO.class);
            dao.createEmailAddress(emailAddressObj);
            session.commit();
        }
    }

    @Override
    public EmailAddress getEmailAddressById(int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(EmailAddressDAO.class);
            return dao.getEmailAddressById(emailAddressId);
        }
    }

    @Override
    public EmailAddress getEmailAddressByEmail(String email) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(EmailAddressDAO.class);
            return dao.getEmailAddressByEmail(email);
        }
    }

    @Override
    public void updateEmailAddress(EmailAddress emailAddressObj) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(EmailAddressDAO.class);
            dao.updateEmailAddress(emailAddressObj);
            session.commit();
        }
    }

    @Override
    public void deleteEmailAddress(int emailAddressId) {
        try (SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession()) {
            EmailAddressDAO dao = session.getMapper(EmailAddressDAO.class);
            dao.deleteEmailAddress(emailAddressId);
            session.commit();
        }
    }
}
