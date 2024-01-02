package com.solvd.airport.persistence;

import com.solvd.airport.domain.Country;
import org.apache.ibatis.annotations.Param;

public interface CountryDAO {
    void createCountry(@Param("countryObj") Country countryObj);
    Country getCountryByCode(@Param("countryCode") String countryCode);
    void updateCountry(@Param("countryObj") Country countryObj);
    void deleteCountry(@Param("countryCode") String countryCode);
    boolean doesCountryCodeExist(@Param("countryCode") String countryCode);
}
