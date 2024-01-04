package com.solvd.airport.persistence;

import com.solvd.airport.domain.Address;
import com.solvd.airport.util.SQLUtils;
import org.apache.ibatis.annotations.Param;

public interface AddressDAO {
    void createAddress(@Param("addressObj") Address addressObj);

    Address getAddressById(@Param("addressId") int addressId);

    Address getAddressByUniqueFields(@Param("street") String street,
                                     @Param("city") String city,
                                     @Param("postalCode") String postalCode,
                                     @Param("countryCode") String countryCode
    );

    void updateAddress(@Param("addressObj") Address addressObj);

    void deleteAddress(@Param("addressId") int addressId);

    String TABLE_NAME = "addresses";
    String ALL_COLUMNS = SQLUtils.qualifyTableWithWildcard(TABLE_NAME);

    String COL_ADDRESS_ID = "address_id";
    String COL_STREET = "street";
    String COL_CITY_SUBDIVISION = "city_subdivision";
    String COL_CITY = "city";
    String COL_CITY_SUPERDIVISION = "city_superdivision";
    String COL_COUNTRY_CODE = CountryDAO.COL_COUNTRY_CODE;
    String COL_POSTAL_CODE = "postal_code";
    String COL_TIMEZONE = TimezoneDAO.COL_TIMEZONE;
    String EXPLICIT_COL_ADDRESS_ID = SQLUtils.qualifyColumnName(TABLE_NAME, COL_ADDRESS_ID);
    String EXPLICIT_COL_STREET = SQLUtils.qualifyColumnName(TABLE_NAME, COL_STREET);
    String EXPLICIT_COL_CITY_SUBDIVISION = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CITY_SUBDIVISION);
    String EXPLICIT_COL_CITY = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CITY);
    String EXPLICIT_COL_CITY_SUPERDIVISION = SQLUtils.qualifyColumnName(TABLE_NAME, COL_CITY_SUPERDIVISION);
    String EXPLICIT_COL_COUNTRY_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_COUNTRY_CODE);
    String EXPLICIT_COL_POSTAL_CODE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_POSTAL_CODE);
    String EXPLICIT_COL_TIMEZONE = SQLUtils.qualifyColumnName(TABLE_NAME, COL_TIMEZONE);
}
