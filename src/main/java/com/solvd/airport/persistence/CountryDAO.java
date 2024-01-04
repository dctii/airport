package com.solvd.airport.persistence;

import com.solvd.airport.domain.Country;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface CountryDAO {
    void createCountry(@Param("countryObj") Country countryObj);

    Country getCountryByCode(@Param("countryCode") String countryCode);

    void updateCountry(@Param("countryObj") Country countryObj);

    void deleteCountry(@Param("countryCode") String countryCode);

    boolean doesCountryCodeExist(@Param("countryCode") String countryCode);

    String TABLE_NAME = "countries";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_COUNTRY_CODE = "country_code";
    String COL_COUNTRY_NAME = "country_name";
    String EXPLICIT_COL_COUNTRY_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_COUNTRY_CODE);
    String EXPLICIT_COL_COUNTRY_NAME = SQLUtils.qualifyColumnName(TABLE_NAME, COL_COUNTRY_NAME);


}
