package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.dao.PersonInfoDAO;
import com.solvd.airport.db.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonInfoDAOImpl implements PersonInfoDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM person_info WHERE person_info_id = ?";
    private static final String FIND_BY_NAME_SQL =
            "SELECT * FROM person_info WHERE surname = ? AND given_name = ?";

    @Override
    public PersonInfo findById(int id) {
        PersonInfo personInfo = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    personInfo = new PersonInfo();
                    personInfo.setPersonInfoId(rs.getInt("person_info_id"));
                    personInfo.setSurname(rs.getString("surname"));
                    personInfo.setGivenName(rs.getString("given_name"));
                    personInfo.setMiddleName(rs.getString("middle_name"));
                    personInfo.setBirthdate(rs.getDate("birthdate"));
                    personInfo.setSex(rs.getString("sex"));
                    personInfo.setAddressId(rs.getInt("address_id"));
                    personInfo.setContactInfoId(rs.getInt("contact_info_id"));
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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_NAME_SQL)
        ) {
            statement.setString(1, surname);
            statement.setString(2, givenName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    personInfo = new PersonInfo();
                    personInfo.setPersonInfoId(rs.getInt("person_info_id"));
                    personInfo.setSurname(rs.getString("surname"));
                    personInfo.setGivenName(rs.getString("given_name"));
                    personInfo.setMiddleName(rs.getString("middle_name"));
                    personInfo.setBirthdate(rs.getDate("birthdate"));
                    personInfo.setSex(rs.getString("sex"));
                    personInfo.setAddressId(rs.getInt("address_id"));
                    personInfo.setContactInfoId(rs.getInt("contact_info_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personInfo;
    }

}
