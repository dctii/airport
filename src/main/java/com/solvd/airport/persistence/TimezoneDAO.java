package com.solvd.airport.persistence;

import com.solvd.airport.domain.Timezone;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface TimezoneDAO extends AbstractDAO<Timezone> {
    int create(@Param("timezoneObj") Timezone timezoneObj);

    Timezone getById(@Param("timezoneId") int timezoneId);

    Timezone getTimezoneByTz(@Param("timezone") String timezone);

    void update(@Param("timezoneObj") Timezone timezoneObj);

    void delete(@Param("timezoneId") int timezoneId);

    void deleteTimezoneByTz(@Param("timezone") String timezone);

    boolean doesTimezoneExist(@Param("timezone") String timezone);

    String TABLE_NAME = "timezones";
    String COL_TIMEZONE_ID = "timezone_id";
    String COL_TIMEZONE = "timezone";
    String EXPLICIT_COL_TIMEZONE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TIMEZONE);

}
