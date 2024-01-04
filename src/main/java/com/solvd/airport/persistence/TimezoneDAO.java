package com.solvd.airport.persistence;

import com.solvd.airport.util.SQLUtils;

public interface TimezoneDAO {
    String TABLE_NAME = "timezones";
    String COL_TIMEZONE = "timezone";
    String EXPLICIT_COL_TIMEZONE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TIMEZONE);

    // DAO Methods
}
