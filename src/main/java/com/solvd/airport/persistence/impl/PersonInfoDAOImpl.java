package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class PersonInfoDAOImpl implements PersonInfoDAO {
    private static final Logger LOGGER = LogManager.getLogger(PersonInfoDAOImpl.class);
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_PERSON_INFO_SQL =
            "INSERT INTO person_info (surname, given_name, middle_name, birthdate, sex) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM person_info WHERE person_info_id = ?";
    private static final String FIND_BY_NAME_SQL =
            "SELECT * FROM person_info WHERE surname = ? AND given_name = ?";
    private static final String UPDATE_PERSON_INFO_SQL =
            "UPDATE person_info SET surname = ?, given_name = ?, middle_name = ?, birthdate = ?, sex = ? WHERE person_info_id = ?";
    private static final String DELETE_PERSON_INFO_SQL =
            "DELETE FROM person_info WHERE person_info_id = ?";

    @Override
    public void createPersonInfo(PersonInfo personInfoObj) {
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_PERSON_INFO_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, personInfoObj.getSurname());
            ps.setString(2, personInfoObj.getGivenName());
            ps.setString(3, personInfoObj.getMiddleName());
            ps.setDate(4, personInfoObj.getBirthdate());
            ps.setString(5, personInfoObj.getSex());

            ps.executeUpdate();

            SQLUtils.setGeneratedKey(ps, personInfoObj::setPersonInfoId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonInfo getPersonInfoById(int personInfoId) {
        PersonInfo personInfo = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, personInfoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personInfo = extractPersonInfoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personInfo;
    }

    @Override
    public PersonInfo findByName(String surname, String givenName) {
        PersonInfo personInfo = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_NAME_SQL)) {
            ps.setString(1, surname);
            ps.setString(2, givenName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personInfo = extractPersonInfoFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personInfo;
    }

    @Override
    public void updatePersonInfo(PersonInfo personInfoObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_INFO_SQL)) {
            ps.setString(1, personInfoObj.getSurname());
            ps.setString(2, personInfoObj.getGivenName());
            ps.setString(3, personInfoObj.getMiddleName());
            ps.setDate(4, personInfoObj.getBirthdate());
            ps.setString(5, personInfoObj.getSex());
            ps.setInt(6, personInfoObj.getPersonInfoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePersonInfo(int personInfoId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_INFO_SQL)) {
            ps.setInt(1, personInfoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PersonInfo extractPersonInfoFromResultSet(ResultSet rs) throws SQLException {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonInfoId(rs.getInt("person_info_id"));
        personInfo.setSurname(rs.getString("surname"));
        personInfo.setGivenName(rs.getString("given_name"));
        personInfo.setMiddleName(rs.getString("middle_name"));
        personInfo.setBirthdate(rs.getDate("birthdate"));
        personInfo.setSex(rs.getString("sex"));
        return personInfo;
    }
}
