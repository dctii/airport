package com.solvd.airport.persistence;

import com.solvd.airport.domain.Timezone;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface TimezoneDAO {
    void createTimezone(@Param("timezoneObj") Timezone timezoneObj);

    Timezone getTimezoneByTz(@Param("timezone") String timezone);

    void updateTimezoneByTz(@Param("oldTimezone") String oldTimezoneString, @Param("newTimezoneObj") Timezone newTimezoneObj);

    void deleteTimezone(@Param("timezone") String timezone);

    boolean doesTimezoneExist(@Param("timezone") String timezone);

    String TABLE_NAME = "timezones";
    String COL_TIMEZONE = "timezone";
    String EXPLICIT_COL_TIMEZONE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TIMEZONE);

}
