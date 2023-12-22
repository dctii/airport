package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.AirportStaffMember;
import com.solvd.airport.persistence.dao.AirportStaffMemberDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportStaffMemberDAOImpl implements AirportStaffMemberDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String FIND_BY_PERSON_INFO_ID_SQL =
            "SELECT * FROM airport_staff WHERE person_info_id = ?";

    @Override
    public AirportStaffMember findByPersonInfoId(int personInfoId) {
        AirportStaffMember staffMember = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_PERSON_INFO_ID_SQL)
        ) {
            statement.setInt(1, personInfoId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    staffMember = new AirportStaffMember();
                    staffMember.setAirportStaffId(rs.getInt("airport_staff_id"));
                    staffMember.setMemberRole(rs.getString("member_role"));
                    staffMember.setPersonInfoId(rs.getInt("person_info_id"));
                    staffMember.setAirportId(rs.getString("airport_id"));
                    staffMember.setAirportTeamId(rs.getInt("airport_team_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffMember;
    }

}
