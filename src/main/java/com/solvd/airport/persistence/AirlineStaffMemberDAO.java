package com.solvd.airport.persistence;

import com.solvd.airport.domain.AirlineStaffMember;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface AirlineStaffMemberDAO extends AbstractDAO<AirlineStaffMember> {
    int create(@Param("airlineStaffMemberObj") AirlineStaffMember airlineStaffMemberObj);

    AirlineStaffMember getById(@Param("airlineStaffId") int airlineStaffId);

    AirlineStaffMember getByPersonInfoId(@Param("personInfoId") int personInfoId);

    AirlineStaffMember getByEmailAddress(@Param("emailAddress") String emailAddress);

    void update(@Param("airlineStaffMemberObj") AirlineStaffMember airlineStaffMemberObj);

    void delete(@Param("airlineStaffId") int airlineStaffId);


    String TABLE_NAME = "airline_staff";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);
    String COL_AIRLINE_STAFF_ID = "airline_staff_id";
    String COL_MEMBER_ROLE = "member_role";
    String COL_PERSON_INFO_ID = PersonInfoDAO.COL_PERSON_INFO_ID;
    String EXPLICIT_COL_AIRLINE_STAFF_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_AIRLINE_STAFF_ID);
    String EXPLICIT_COL_MEMBER_ROLE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_MEMBER_ROLE);
    String EXPLICIT_COL_PERSON_INFO_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_PERSON_INFO_ID);
}
