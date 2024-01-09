package com.solvd.airport.persistence.impl;

import com.solvd.airport.util.DBConnectionPool;
import com.solvd.airport.domain.PersonInfo;
import com.solvd.airport.persistence.PersonInfoDAO;
import com.solvd.airport.util.SQLConstants;
import com.solvd.airport.util.SQLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.List;

public class PersonInfoJDBCImpl implements PersonInfoDAO {
    private static final Logger LOGGER = LogManager.getLogger(PersonInfoJDBCImpl.class);
    private final DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final DSLContext create = DSL.using(SQLDialect.MYSQL);


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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)
        ) {
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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_NAME_SQL)
        ) {
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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_PERSON_INFO_SQL)
        ) {
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
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_PERSON_INFO_SQL)
        ) {
            ps.setInt(1, personInfoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PersonInfo extractPersonInfoFromResultSet(ResultSet rs) throws SQLException {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setPersonInfoId(rs.getInt(COL_PERSON_INFO_ID));
        personInfo.setSurname(rs.getString(COL_SURNAME));
        personInfo.setGivenName(rs.getString(COL_GIVEN_NAME));
        personInfo.setMiddleName(rs.getString(COL_MIDDLE_NAME));
        personInfo.setBirthdate(rs.getDate(COL_BIRTHDATE));
        personInfo.setSex(rs.getString(COL_SEX));
        return personInfo;
    }

        /*
        "INSERT INTO person_info (surname, given_name, middle_name, birthdate, sex) VALUES (?, ?, ?, ?, ?)";
    */

    private static final List<Field<?>> INSERT_PERSON_INFO_FIELDS = List.of(
            DSL.field(COL_SURNAME),
            DSL.field(COL_GIVEN_NAME),
            DSL.field(COL_MIDDLE_NAME),
            DSL.field(COL_BIRTHDATE),
            DSL.field(COL_SEX)
    );

    private static final String INSERT_PERSON_INFO_SQL = create
            .insertInto(DSL.table(TABLE_NAME), INSERT_PERSON_INFO_FIELDS)
            .values(SQLUtils.createPlaceholders(INSERT_PERSON_INFO_FIELDS.size()))
            .getSQL();

    /*
        "SELECT * FROM person_info WHERE person_info_id = ?";
     */

    private static final String FIND_BY_ID_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    /*
        "SELECT * FROM person_info WHERE surname = ? AND given_name = ?";
     */

    private static final String FIND_BY_NAME_SQL = create
            .selectFrom(DSL.table(TABLE_NAME))
            .where(
                    SQLUtils.eqPlaceholder(COL_SURNAME)
                            .and(SQLUtils.eqPlaceholder(COL_GIVEN_NAME))
            )
            .getSQL();

    /*
        "UPDATE person_info SET surname = ?, given_name = ?, middle_name = ?, birthdate = ?, sex = ? WHERE person_info_id = ?";
     */

    private static final String UPDATE_PERSON_INFO_SQL = create
            .update(DSL.table(TABLE_NAME))
            .set(DSL.field(COL_SURNAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_GIVEN_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_MIDDLE_NAME), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_BIRTHDATE), SQLConstants.PLACEHOLDER)
            .set(DSL.field(COL_SEX), SQLConstants.PLACEHOLDER)
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();

    /*
        "DELETE FROM person_info WHERE person_info_id = ?";
     */

    private static final String DELETE_PERSON_INFO_SQL = create
            .deleteFrom(DSL.table(TABLE_NAME))
            .where(SQLUtils.eqPlaceholder(COL_PERSON_INFO_ID))
            .getSQL();
}
