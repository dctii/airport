package com.solvd.airport.persistence.mappers;

import com.solvd.airport.domain.Address;

public interface AddressDAO {
    void createAddress(Address address);
    Address getAddressById(int id);
    Address getAddressByUniqueFields(String street, String city, String postalCode, String countryCode);
    void updateAddress(Address address);
    void deleteAddress(int id);
}
