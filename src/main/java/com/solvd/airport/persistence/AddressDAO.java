package com.solvd.airport.persistence;

import com.solvd.airport.domain.Address;
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
}
