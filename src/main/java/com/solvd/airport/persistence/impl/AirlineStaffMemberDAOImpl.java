package com.solvd.airport.persistence.impl;

import com.solvd.airport.db.DBConnectionPool;
import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.persistence.AirlineStaffMemberDAO;

import java.sql.*;

public class AirlineStaffMemberDAOImpl implements AirlineStaffMemberDAO {
    private DBConnectionPool connectionPool = DBConnectionPool.getInstance();

    private static final String INSERT_AIRLINE_STAFF_SQL =
            "INSERT INTO airline_staff (member_role, person_info_id) VALUES (?, ?)";
    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM airline_staff WHERE airline_staff_id = ?";
    private static final String FIND_BY_PERSON_INFO_ID_SQL =
            "SELECT * FROM airline_staff WHERE person_info_id = ?";
    private static final String UPDATE_AIRLINE_STAFF_SQL =
            "UPDATE airline_staff SET member_role = ?, person_info_id = ? WHERE airline_staff_id = ?";
    private static final String DELETE_AIRLINE_STAFF_SQL =
            "DELETE FROM airline_staff WHERE airline_staff_id = ?";

    @Override
    public void createAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_AIRLINE_STAFF_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, staffMemberObj.getMemberRole());
            ps.setInt(2, staffMemberObj.getPersonInfoId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating airline staff member failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    staffMemberObj.setAirlineStaffId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating airline staff member failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AirlineStaffMember getAirlineStaffMemberById(int airlineStaffId) {
        AirlineStaffMember staffMember = null;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {
            ps.setInt(1, airlineStaffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staffMember = new AirlineStaffMember();
                    staffMember.setAirlineStaffId(rs.getInt("airline_staff_id"));
                    staffMember.setMemberRole(rs.getString("member_role"));
                    staffMember.setPersonInfoId(rs.getInt("person_info_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffMember;
    }

    @Override
    public AirlineStaffMember findByPersonInfoId(int personInfoId) {
        AirlineStaffMember staffMember = null;
        try (
                Connection conn = connectionPool.getConnection();
                PreparedStatement statement = conn.prepareStatement(FIND_BY_PERSON_INFO_ID_SQL)
        ) {
            statement.setInt(1, personInfoId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    staffMember = new AirlineStaffMember();
                    staffMember.setAirlineStaffId(rs.getInt("airline_staff_id"));
                    staffMember.setMemberRole(rs.getString("member_role"));
                    staffMember.setPersonInfoId(rs.getInt("person_info_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffMember;
    }

    @Override
    public AirlineStaffMember findByEmailAddress(String emailAddress) {
        AirlineStaffMember staffMember = null;
        String query = "SELECT a.* FROM airline_staff a " +
                "JOIN person_info p ON a.person_info_id = p.person_info_id " +
                "JOIN person_email_addresses pea ON p.person_info_id = pea.person_info_id " +
                "JOIN email_addresses e ON pea.email_address_id = e.email_address_id " +
                "WHERE e.email_address = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, emailAddress);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staffMember = new AirlineStaffMember();
                    staffMember.setAirlineStaffId(rs.getInt("airline_staff_id"));
                    staffMember.setMemberRole(rs.getString("member_role"));
                    staffMember.setPersonInfoId(rs.getInt("person_info_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffMember;
    }

    @Override
    public void updateAirlineStaffMember(AirlineStaffMember staffMemberObj) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_AIRLINE_STAFF_SQL)) {
            ps.setString(1, staffMemberObj.getMemberRole());
            ps.setInt(2, staffMemberObj.getPersonInfoId());
            ps.setInt(3, staffMemberObj.getAirlineStaffId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAirlineStaffMember(int airlineStaffId) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_AIRLINE_STAFF_SQL)) {
            ps.setInt(1, airlineStaffId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
